package com.model;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "STORAGE")
public class Storage {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_SEQ", sequenceName = "STORAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_SEQ")
    private long id;

    @Column(name = "STORAGE_FORMAT")
    private String formatsSupported;

    @Column(name = "STORAGE_COUNTRY")
    private String storageCountry;

    @Column(name = "STORAGE_SIZE")
    private long storageSize;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storage", fetch = FetchType.EAGER)
    private List<File> files;

    public Storage() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFormatsSupported(String formatsSupported) {
        this.formatsSupported = formatsSupported;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    public long getId() {
        return id;
    }

    public String getFormatsSupported() {
        return formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public long getStorageSize() {
        return storageSize;
    }


    public List<File> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatsSupported='" + formatsSupported + '\'' +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageSize=" + storageSize +
                ", files=" + files +
                '}';
    }
}
