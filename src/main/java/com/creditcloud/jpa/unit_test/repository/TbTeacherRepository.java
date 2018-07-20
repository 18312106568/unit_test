package com.creditcloud.jpa.unit_test.repository;

import com.creditcloud.jpa.unit_test.entity.TbTeacher;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author MRB
 */
public interface TbTeacherRepository extends JpaRepository<TbTeacher, String> {

}
