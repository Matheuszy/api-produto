package br.com.alura.produto.application.aop;

import com.google.gson.Gson;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;
import java.util.StringJoiner;
import java.util.TreeMap;

import static java.lang.String.format;
import static java.time.LocalTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

@Slf4j
@Aspect
@Component
@Observed
public class LogAspect {

    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Autowired
    private Gson gson;

    @Pointcut("within(br.com.alura..*Controller)")
    public void isController() {

    }

    @Pointcut("within(br.com.alura..*Listener)")
    public void isListener() {

    }

    @Pointcut("within(br.com.alura..*Scheduler)")
    public void isScheduler() {

    }

    @Around("isController() || isListener() || isScheduler()")
    public Object around1(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        Exception exception = null;

        var started = now();
        log(point, started);
        try {
            result = point.proceed();
        } catch (Exception e) {
            exception = e;
        }
        var finished = now();
        log(point, result, exception, started, finished);

        if (exception == null)
            return result;
        else
            throw exception;
    }

    public void log(ProceedingJoinPoint point, LocalTime started) {
        try {
            var method = getMethod(point);

            var message = new StringJoiner("\n")
                    .add(format("Started '%s' at %s", buildMethodName(method), started.format(TF)));

            var args = getArgs(point);
            if (!args.isEmpty())
                message
                        .add("with args:")
                        .add(args.toString());
            log.debug(message.toString());
        } catch(Exception e) {
            log.warn(e.getMessage());
        }
    }

    public void log(ProceedingJoinPoint point, Object result, Exception exception, LocalTime started, LocalTime finished) {
        try {
            var duration = MILLIS.between(started, finished);

            var message = new StringJoiner("\n")
                    .add(format("Finished at %s it took %s millis", finished.format(TF), duration));
            if (exception != null) {
                message.add(format("with exception: %s", exception.getMessage()));
                if (exception.getStackTrace() != null)
                    for (var trace : exception.getStackTrace())
                        message.add("\t\t" + trace.toString());
            } else
                message
                        .add("with return:")
                        .add(buildReturn(result));
            log.debug(message.toString());
        } catch(Exception e) {
            log.warn(e.getMessage());
        }
    }

    private Method getMethod(ProceedingJoinPoint point) {
        var methodSignature = (MethodSignature) point.getSignature();
        return methodSignature.getMethod();
    }

    private String buildMethodName(Method method) {
        var className = buildClassName(method.getDeclaringClass());
        return format("%s.%s", className, method.getName());
    }

    private String buildClassName(Class<?> clazz) {
        var packageName = clazz.getPackageName() + ".";
        var className = clazz.getName().replace(packageName, "");
        return className.replace("$", ".");
    }

    private SortedMap<?, ?> getArgs(ProceedingJoinPoint point) {
        var codeSignature = (CodeSignature) point.getSignature();
        var methodParams = codeSignature.getParameterNames();
        var methodArgs = point.getArgs();

        var map = new TreeMap<>();
        for (var i = 0; i < methodParams.length; i++) {
            var param = methodParams[i];
            var arg = methodArgs[i];
            map.put(param, getAsJson(arg));
        }
        return map;
    }

    private String buildReturn(Object result) {
        if (result == null)
            return "null";
        var jsonRetorno = getAsJson(result);
        var nomeClasseRetorno = buildClassName(result.getClass());
        return format("[%s\":%s}", nomeClasseRetorno, jsonRetorno);
    }

    private String getAsJson(Object object) {
        if (object == null)
            return null;

        if (object instanceof String string)
            return string;

        try {
            return gson.toJson(object);

        } catch (Exception | StackOverflowError e) {
            log.warn(e.getMessage());
            return new ReflectionToStringBuilder(object, JSON_STYLE)
                    .toString();
        }
    }
}
