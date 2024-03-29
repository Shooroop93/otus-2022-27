package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;
    private final String tableName;
    private final String columnNameOfId;
    private final String listColumnNamesWithoutId;
    private final String listExpressionsForColumnNamesWithoutId;
    private final String listSetForUpdate;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        tableName = entityClassMetaData.getName().toLowerCase();
        columnNameOfId = entityClassMetaData.getIdField().getName().toLowerCase();
        listColumnNamesWithoutId = entityClassMetaData.getFieldsWithoutId().stream().
                map(Field::getName)
                .collect(Collectors.joining(", ", "(", ")"));
        listExpressionsForColumnNamesWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
                .map(s -> "?")
                .collect(Collectors.joining(", ", "(", ")"));
        listSetForUpdate = entityClassMetaData.getFieldsWithoutId().stream()
                .map(s -> s.getName() + " = ?")
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + tableName;
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + tableName + " where " + columnNameOfId + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "insert into " + tableName + listColumnNamesWithoutId + " values " + listExpressionsForColumnNamesWithoutId;
    }

    @Override
    public String getUpdateSql() {
        return "update " + tableName + " set " + listSetForUpdate + " where " + columnNameOfId + " = ?";
    }
}