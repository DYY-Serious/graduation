package com.zua.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReturnBook implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String bookId;
    private String bookName;
    private String bookPlaceNum;
    private String studentName;
    private String studentId;
    private String className;
    private String phone;
    private String borrowStatus;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date borrowTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date returnTime;
}
