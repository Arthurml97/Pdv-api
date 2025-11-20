package br.com.tutum.portfolio.pdvapi.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

// Exceção personalizada para regras de negócio
// Pode ser lançada quando uma regra de negócio é violada
// Por exemplo, ao tentar cadastrar um produto com um código de barras já existente
// ou ao tentar realizar uma operação inválida no sistema
// Não precisa de try-catch específico, pode ser tratada globalmente
@ResponseStatus(HttpStatus.BAD_REQUEST) // Se esta exceção for lançada, retorne ao código HTTP 400
public class RegraDeNegocioException extends RuntimeException {
    // Construtor que recebe uma mensagem de erro
    public RegraDeNegocioException(String message) {
        super(message); // Chama o construtor da classe pai (RuntimeException) com a mensagem
    }
}
