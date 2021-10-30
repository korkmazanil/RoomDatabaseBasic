package com.korkmazanil.roomdatabasebasic.util;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.korkmazanil.roomdatabasebasic.data.ToDoListDao;
import com.korkmazanil.roomdatabasebasic.model.ToDoList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ToDoList.class}, version = 1, exportSchema = false)
public abstract class ToDoListRoomDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile ToDoListRoomDatabase INSTANCE;

    private static final Callback sRoomDatabaseCallback =
            new Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {

                        ToDoListDao toDoListDao = INSTANCE.toDoListDao();
                        toDoListDao.deleteAll();

                    });
                }
            };

    public static  ToDoListRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ToDoListRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoListRoomDatabase.class,"to_do_list_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract ToDoListDao toDoListDao();
}
