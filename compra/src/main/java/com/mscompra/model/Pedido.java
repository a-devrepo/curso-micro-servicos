package com.mscompra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Entity(name = "tb_pedido")
public class Pedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 7335789400519836017L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @NotNull
    @Min(1)
    private Long produto;
    @NotNull
    @Min(1)
    private BigDecimal valor;
    @NotNull
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dataCompra;
    @NotBlank
    private String cpfCliente;
    @NotBlank
    private String cep;
    @NotBlank
    private String email;

}
