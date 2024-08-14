package net.hasni.ensetdemospringangular.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AffectationStudentCoursDTO implements Serializable {

    private List<Integer> coursIds;
    private Integer studentId;
}
