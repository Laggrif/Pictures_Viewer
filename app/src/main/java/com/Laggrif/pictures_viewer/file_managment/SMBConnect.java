package com.Laggrif.pictures_viewer.file_managment;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.List;

public class SMBConnect {
    @Entity
    public class Server {
        @PrimaryKey
        @NonNull
        public String name;

        @ColumnInfo(name = "custom_name")
        public String customName;

        @ColumnInfo(name = "address")
        public String address;

        @ColumnInfo(name = "path")
        public String path;

        @ColumnInfo(name = "user")
        public String user;

        @ColumnInfo(name = "password")
        public String password;

        public Server() {
            name = "";
        }
    }

    @Dao
    public interface ServerDAO {
        @Insert
        void insertServer(Server server);

        @Update
        void updateServer(Server server);

        @Delete
        void deleteServer(Server server);

        @Query("SELECT * FROM server")
        List<Server> getAll();

        @Query("SELECT custom_name FROM Server")
        List<Server> loadCustomName();
    }

    @Database(entities = {Server.class}, version = 1)
    public abstract class ServerDatabase extends androidx.room.RoomDatabase {
        public abstract ServerDAO serverDAO();
    }

}

