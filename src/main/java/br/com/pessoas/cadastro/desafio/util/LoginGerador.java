package br.com.pessoas.cadastro.desafio.util;

import java.util.Arrays;
import java.util.Set;

public class LoginGerador {

    public static String gerarlogin(String nomeNormalizado, Set<String> conjuntoLogins) {
        String nomeSemEspaco = nomeNormalizado.replace(" ", "");
        String login;

        for (int i=0; i <= nomeSemEspaco.length() - 7; i++) {
            login = nomeSemEspaco.substring(i, i+7);

            if (!conjuntoLogins.contains(login)) {
                return login;
            }
        }

        return gerarLoginFallback(nomeNormalizado, conjuntoLogins);
    }

    private static String gerarLoginFallback(String nomeNormalizado, Set<String> conjuntoLogins) {
        String[] partesNome = nomeNormalizado.split(" ");

        String primeiroNome = partesNome[0];
        String sobrenomeJunto = String.join("", Arrays.copyOfRange(partesNome, 1, partesNome.length));

        int ponteiro = 7 - primeiroNome.length();
        for (int i = 0; i <= sobrenomeJunto.length() - ponteiro; i++) {
            String login = primeiroNome + sobrenomeJunto.substring(i, i+ponteiro);

            if (!conjuntoLogins.contains(login)) {
                return login;
            }

        }

        return "";
    }
}
