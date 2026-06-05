package br.com.pessoas.cadastro.desafio.util;

import java.text.Normalizer;

/**
 * Utilitário responsável pela normalização do nome da pessoa antes da geração do login.
 *
 * O processo de normalização garante que o login seja composto apenas por
 * letras minúsculas de A-Z, conforme exigido pelas regras do sistema.
 *
 * Processo aplicado:
 * 1. Substitui 'ç' e 'Ç' por 'c' e 'C' — a cedilha não é tratada pelo NFD
 * 2. Aplica normalização NFD — decompõe caracteres acentuados em letra base + acento
 *    Exemplo: 'ã' → 'a' + '~'
 * 3. Remove todos os caracteres não ASCII — elimina os acentos decompostos
 * 4. Converte para minúsculo
 * 5. Remove espaços nas extremidades
 */
public class NomeNormalizador {

    /**
     * Normaliza o nome completo removendo acentos, cedilha e convertendo para minúsculo.
     *
     * @param nomeCompleto nome completo da pessoa como informado no cadastro
     * @return nome normalizado contendo apenas letras minúsculas de a-z e espaços simples
     */
    public static String normalizarNome(String nomeCompleto) {
        return Normalizer.normalize(nomeCompleto.replace("ç", "c").replace("Ç", "c"), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .trim();
    }
}
