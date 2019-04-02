package com.diasonti.descriptiontinder.utils;

import org.hibernate.Hibernate;

public class EntityUtils {

    @SuppressWarnings("unchecked")
    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            return null;
        }

        Hibernate.initialize(entity);
        if (entity instanceof org.hibernate.proxy.HibernateProxy) {
            entity = (T) ((org.hibernate.proxy.HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

}
