package com.rga.gradesubmission.domain.documents;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.rga.gradesubmission.domain.utilities.ToUpperCaseDeserializer;
import com.rga.gradesubmission.domain.validation.email.Curp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements Serializable {

    @Id()
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @Field()
    @NotBlank(message = "email cannot be blank")
    @Email()
    @Indexed(unique = true)
    private String email;

    @Field()
    @NotBlank(message = "password cannot be blank")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Field(name = "CURP")
    @NotBlank(message = "curp field cannot be blank")
    @Curp(message = "the curp field has not valid pattern",verifyLength = true)
    @JsonDeserialize(using = ToUpperCaseDeserializer.class)
    private String curp;
}
