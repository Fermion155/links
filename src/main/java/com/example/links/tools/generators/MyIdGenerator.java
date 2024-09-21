package com.example.links.tools.generators;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;
import java.util.List;


public class MyIdGenerator implements BeforeExecutionGenerator {

    private static final String alphaNumericalSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

    @Override
    public String generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        String id = "";
        do {
            id = "";
            for (int i = 0; i < 7; i++) {
                id += alphaNumericalSymbols.charAt((int) (Math.random() * (alphaNumericalSymbols.length())));
            }
        } while (idAlreadyExists(id, session));
        return id;
    }

    private boolean idAlreadyExists(String id, SharedSessionContractImplementor session) {
        String query = "SELECT id FROM Link l WHERE l.id = :id";
        List<String> result = session.createQuery(query, String.class)
                .setParameter("id", id)
                .list();
        System.out.println("size: " + result.size());
        return !result.isEmpty();
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }
}