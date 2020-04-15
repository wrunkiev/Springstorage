package com.model;


import javax.persistence.*;

@Entity
@Table(name = "FILES")
public class File {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "F_SEQ", sequenceName = "FILE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "F_SEQ")
    private long id;

    @Column(name = "FILE_NAME")
    private String name;

    @Column(name = "FILE_FORMAT")
    private String format;

    @Column(name = "FILE_SIZE")
    private long size;

    @ManyToOne
    @JoinColumn(name = "STORAGE_ID", nullable = false)
    private Storage storage;

    public File() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public long getSize() {
        return size;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}