package br.ufal.ic.p2.wepayu.Banking;

import br.ufal.ic.p2.wepayu.Managment.Manage;
import br.ufal.ic.p2.wepayu.Models.Empregado;
import br.ufal.ic.p2.wepayu.Models.KindCard.CardService;
import br.ufal.ic.p2.wepayu.Utils.Utils;


import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
    Classe criada para gerenciar e manipular o armazenamento de dados no arquivo XML

 */

public class Banking {

    static String enderecoListaEmpregados = "./listaEmpregados.xml"; //Endereço e nome do XML
    public Banking(){// Construtor vazio para escrita no XML

    }
    private static HashMap<String, Empregado> empregados = (HashMap<String, Empregado>) Utils.carregarEmpregadosDeXML("./listaEmpregados.xml");// Carregando a hashmap a partir do XML

    public static String getEnderecoListaEmpregados(){// Retorna o endereço do arquivo XML
        return enderecoListaEmpregados;
    }


    public static void updateEmployByXML(){// Atualiza o vetor de Empregados de Management a partir do arquivo XML
        Manage.employee = Utils.carregarEmpregadosDeXML(enderecoListaEmpregados);
    }

    public static void zerarSystem(){// Método para zerar o sistema
        empregados.clear();
        Manage.employee = new HashMap<>();
        Manage.key = 0;
        Utils.saveXML(empregados, enderecoListaEmpregados);
    }
}
