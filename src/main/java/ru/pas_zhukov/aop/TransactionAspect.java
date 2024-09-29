package ru.pas_zhukov.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TransactionAspect {

    private final SessionFactory sessionFactory;

    public TransactionAspect(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Around("@annotation(TransactionalAction)")
    public Object executeInTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            // Внедряем сессию как аргумент, если метод принимает Session
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Session) {
                    args[i] = session;
                }
            }

            // Выполняем исходный метод
            Object result = joinPoint.proceed(args);

            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
