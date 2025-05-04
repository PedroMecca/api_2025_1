package br.edu.ifsp.prw3.api_2025_1.Models;






import jakarta.persistence.*;
import lombok.*;

@Table(name = "consertos")
@Entity(name = "Conserto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conserto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean ativo;
    private String dataEntrada;
    private String dataSaida;

    @Embedded
    private Mecanico mecanico;

    @Embedded
    private Veiculo veiculo;

}
