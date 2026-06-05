package br.com.pessoas.cadastro.desafio.util;

import java.util.Arrays;
import java.util.Set;

/**
 * Utilitário responsável pela geração automática de login para novas pessoas cadastradas.
 *
 * Regras do login gerado:
 * - Exatamente 7 caracteres
 * - Apenas letras minúsculas (a-z)
 * - Sem números e sem espaços
 * - Único no sistema
 * - Construído a partir do nome da pessoa
 *
 * Estratégia de geração:
 * 1. Janela deslizante: percorre o nome completo sem espaços extraindo substrings de 7 caracteres
 * 2. Fallback: caso a janela deslizante se esgote, combina o primeiro nome com
 *    uma janela deslizante sobre os sobrenomes juntos
 *
 * A unicidade é garantida comparando os candidatos contra um Set de logins
 * já existentes carregado previamente do banco de dados — evitando múltiplas
 * consultas ao banco durante o processo de geração.
 */
public class LoginGerador {

    /**
     * Gera um login único de 7 caracteres a partir do nome normalizado.
     *
     * @param nomeNormalizado nome da pessoa já normalizado (sem acentos, sem cedilha, em minúsculo)
     * @param conjuntoLogins  conjunto de logins já existentes no banco, usado para garantir unicidade
     * @return login único de 7 caracteres, ou string vazia se não for possível gerar
     */
    public static String gerarlogin(String nomeNormalizado, Set<String> conjuntoLogins) {
        String nomeSemEspaco = nomeNormalizado.replace(" ", "");
        String login;

        // Estratégia 1 — Janela deslizante sobre o nome completo sem espaços
        // Percorre todas as substrings possíveis de 7 caracteres
        // Exemplo: "mariasilva" → "mariasi", "ariasil", "riasilv", "iasilva"
        for (int i=0; i <= nomeSemEspaco.length() - 7; i++) {
            login = nomeSemEspaco.substring(i, i+7);

            if (!conjuntoLogins.contains(login)) {
                return login;
            }
        }

        // Estratégia 2 — Fallback: primeiro nome + janela deslizante sobre os sobrenomes
        return gerarLoginFallback(nomeNormalizado, conjuntoLogins);
    }

    /**
     * Estratégia de fallback para geração de login quando a janela deslizante
     * principal se esgota sem encontrar um login disponível.
     *
     * Combina o primeiro nome completo com uma janela deslizante sobre os sobrenomes
     * concatenados, completando sempre até 7 caracteres.
     *
     * Exemplo: nome "Ana Clara Lima"
     * - primeiroNome = "ana" (3 chars)
     * - sobrenomeJunto = "claralima"
     * - ponteiro = 7 - 3 = 4
     * - Candidatos: "anaclar", "analara", "anaaral", "anaralim"...
     *
     * @param nomeNormalizado nome normalizado com espaços preservados para separação
     * @param conjuntoLogins  conjunto de logins existentes para validação em memória
     * @return login único de 7 caracteres, ou string vazia se esgotadas as possibilidades
     */
    private static String gerarLoginFallback(String nomeNormalizado, Set<String> conjuntoLogins) {
        String[] partesNome = nomeNormalizado.split(" ");

        String primeiroNome = partesNome[0];
        String sobrenomeJunto = String.join("", Arrays.copyOfRange(partesNome, 1, partesNome.length));

        // Calcula quantos caracteres do sobrenome são necessários para completar 7
        int ponteiro = 7 - primeiroNome.length();
        for (int i = 0; i <= sobrenomeJunto.length() - ponteiro; i++) {
            String login = primeiroNome + sobrenomeJunto.substring(i, i+ponteiro);

            if (!conjuntoLogins.contains(login)) {
                return login;
            }

        }

        // Retorna vazio se todas as combinações possíveis já estiverem em uso
        return "";
    }
}
