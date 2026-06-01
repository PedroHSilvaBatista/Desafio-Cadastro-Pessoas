package br.com.pessoas.cadastro.desafio.util;

import java.text.Normalizer;

public class NomeNormalizador {

    public static String normalizarNome(String nomeCompleto) {
        return Normalizer.normalize(nomeCompleto.replace("ç", "c").replace("Ç", "c"), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .trim();
    }
}
