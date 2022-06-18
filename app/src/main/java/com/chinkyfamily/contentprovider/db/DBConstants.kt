package com.chinkyfamily.contentprovider.db

object DBConstants
{
    const val DB_NAME : String = "todo_db"
    const val DB_VERSION : Int = 1
    const val TABLE_NAME : String = "todo_table"
    const val COLUMN_TODO_ID : String = "todo_id"
    const val COLUMN_TODO : String = "todo"
    const val CREATE_QUERY : String =
        "CREATE TABLE $TABLE_NAME ($COLUMN_TODO_ID INTEGER PRIMARY KEY, $COLUMN_TODO TEXT NOT NULL)"
    const val INSERT_QUERY : String = "INSERT INTO $TABLE_NAME"
}