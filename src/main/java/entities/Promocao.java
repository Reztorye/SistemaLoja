package entities;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promocao {
    private Produto produto;
    private double percentualDesconto;
    private Date dataInicio;
    private Date dataFim;

    // Método para verificar se a promoção está ativa com base na data atual
    public boolean estaAtiva() {
        Date agora = new Date();
        return agora.after(dataInicio) && agora.before(dataFim);
    }
}
