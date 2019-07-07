package com.ada.learning;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * make sure the file(conf/hbase-env.sh) is correct exist on running host:
 * export HBASE_CLASSPATH=/usr/local/hbase/conf
 */
@Slf4j
public class HBaseOperation {
    private static Configuration configuration;
    private static Connection connection;
    private static Admin admin;

    public static void main(String[] args) {
        init();
        createTable("t2", new String[]{"cf1","cf2"});
        insertRows("t2", "rw1", "cf1", "q1", "val1");
        insertRows("t2", "rw2", "cf2", "q2", "val2");
        getData("t2", "rw1", "cf1", "q1");
        getData("t2", "rw2", "cf2", "q2");
        close();
    }

    @SneakyThrows
    public static void createTable(String tableName, String[] columnFamily){
        TableName table = TableName.valueOf(tableName);
        if (admin.tableExists(table)){
            log.warn("table is Exists");
            return;
        }

        HTableDescriptor hTable = new HTableDescriptor(table);
        for (String column : columnFamily) {
            hTable.addFamily(new HColumnDescriptor(column));
        }

        admin.createTable(hTable);
    }

    @SneakyThrows
    public static void deleteTable(String tableName){
        TableName table = TableName.valueOf(tableName);
        if (admin.tableExists(table)){
            admin.disableTable(table);
            admin.deleteTable(table);
            log.warn("success!table is deleted");
        }else {
            log.warn("table not exists");
        }
    }

    @SneakyThrows
    public static void listTables(String tableName){
        for (HTableDescriptor hTableDescriptor : admin.listTables()) {
            log.warn("listTable:" + hTableDescriptor.getNameAsString());
        }
    }

    @SneakyThrows
    public static void insertRows(String tableName,String rowKey, String columnFamily, String row, String val){
        try (Table table = connection.getTable(TableName.valueOf(tableName))){
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(row),Bytes.toBytes(val));
            table.put(put);
        }
    }


    @SneakyThrows
    public static void deleteRows(String tableName,String rowKey, String columnFamily, String row){
        try (Table table = connection.getTable(TableName.valueOf(tableName))){
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(row));
            table.delete(delete);
        }
    }

    @SneakyThrows
    public static void getData(String tableName,String rowKey, String columnFamily, String row){
        try (Table table = connection.getTable(TableName.valueOf(tableName))){
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(row));
            Result result = table.get(get);
            showCell(result);
        }
    }

    public static void showCell(Result result) {
        for (Cell cell : result.rawCells()) {
            String str = "rowName:%s-family:%s-qua:%s-val:%s";
            log.warn(String.format(str, new String(CellUtil.cloneRow(cell)),
                    new String(CellUtil.cloneFamily(cell)),
                    new String(CellUtil.cloneQualifier(cell)),
                    new String(CellUtil.cloneValue(cell))));
        }
    }


    @SneakyThrows
    public static void init(){
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.rootdir", "hdfs://localhost:9000//hbase");
        connection = ConnectionFactory.createConnection(configuration);
        admin = connection.getAdmin();
    }

    @SneakyThrows
    public static void close(){
        if (connection != null){
            connection.close();
        }

        if (admin != null){
            admin.close();
        }

    }

}
