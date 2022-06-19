package com.chinkyfamily.contentprovider.db

object DBConstants
{
    /** Name of the Database */
    const val DB_NAME : String = "todo_db"

    /** Version of the Database */
    const val DB_VERSION : Int = 1

    /** Name of a to_do table of the Database */
    const val TODO_TABLE_NAME : String = "todo_table"
    const val COLUMN_TODO_ID : String = "todo_id"
    const val COLUMN_TODO : String = "todo"
    const val CREATE_QUERY : String =
        "CREATE TABLE $TODO_TABLE_NAME ($COLUMN_TODO_ID INTEGER PRIMARY KEY, $COLUMN_TODO TEXT NOT NULL)"
    const val INSERT_QUERY : String = "INSERT INTO $TODO_TABLE_NAME"
}