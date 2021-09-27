package com.example.demo.java_beans.vo.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.domain.Test;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: gin
 * @Date: 2021/9/11 下午3:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestRequest extends Page<Test> {
    private String name;
    private Long id;
}
