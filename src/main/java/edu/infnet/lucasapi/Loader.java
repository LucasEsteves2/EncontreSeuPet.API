package edu.infnet.lucasapi;

import edu.infnet.lucasapi.application.service.AvistamentoService;
import edu.infnet.lucasapi.application.service.PetService;
import edu.infnet.lucasapi.application.service.UsuarioService;
import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.enums.TipoLocalizacao;
import edu.infnet.lucasapi.domain.model.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;


// Professor, usei PostgreSQL via Docker, mas deixei o profile padrão com H2 para rodar sem dependências externas quando voce for rodar.
@Profile("dev")
@Component
public class Loader implements ApplicationRunner {

    private final UsuarioService usuarioService;
    private final PetService petService;
    private final AvistamentoService avistamentoService;

    public Loader(UsuarioService usuarioService, PetService petService, AvistamentoService avistamentoService) {
        this.usuarioService = usuarioService;
        this.petService = petService;
        this.avistamentoService = avistamentoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("🚀 Iniciando carga automática de dados...");

        carregarUsuarios();
        carregarPets();
        carregarAvistamentos();

        System.out.println("✅ Carga inicial concluída com sucesso!");
    }

    private void carregarUsuarios() throws Exception {
        Path arquivo = Path.of("usuarios.csv");
        if (!Files.exists(arquivo)) return;

        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine(); // cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Usuario u = Usuario.builder()
                        .nome(dados[0].trim())
                        .email(dados[1].trim())
                        .senha(dados[2].trim())
                        .build();
                usuarioService.criar(u);
                count++;
            }
        }

        System.out.println("👤 Usuários carregados: " + count);
    }

    private void carregarPets() throws Exception {
        Path arquivo = Path.of("pets.csv");
        if (!Files.exists(arquivo)) return;

        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Long usuarioId = Long.parseLong(dados[5].trim());
                Usuario usuario = usuarioService.buscarPorId(usuarioId);

                Pet p = Pet.builder()
                        .nome(dados[0].trim())
                        .especie(dados[1].trim())
                        .cor(dados[2].trim())
                        .descricao(dados[3].trim())
                        .status(StatusPet.valueOf(dados[4].trim()))
                        .usuario(usuario)
                        .build();

                petService.criar(p);
                count++;
            }
        }

        System.out.println("🐕 Pets carregados: " + count);
    }

    private void carregarAvistamentos() throws Exception {
        Path arquivo = Path.of("avistamentos.csv");
        if (!Files.exists(arquivo)) return;

        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");

                Long petId = Long.parseLong(dados[1].trim());
                Long usuarioId = Long.parseLong(dados[2].trim());

                Pet pet = petService.buscarPorId(petId);
                Usuario usuario = usuarioService.buscarPorId(usuarioId);

                Endereco endereco = Endereco.builder()
                        .rua(dados[5].trim())
                        .numero(dados[6].trim())
                        .bairro(dados[7].trim())
                        .cidade(dados[8].trim())
                        .estado(dados[9].trim())
                        .cep(dados[10].trim())
                        .build();

                Localizacao localizacao = Localizacao.builder()
                        .latitude(Double.parseDouble(dados[3].trim()))
                        .longitude(Double.parseDouble(dados[4].trim()))
                        .endereco(endereco)
                        .tipo(TipoLocalizacao.AVISTAMENTO)
                        .build();

                Avistamento avistamento = Avistamento.builder()
                        .descricao(dados[0].trim())
                        .pet(pet)
                        .usuario(usuario)
                        .localizacao(localizacao)
                        .dataAvistamento(LocalDateTime.now())
                        .build();

                avistamentoService.criarAvistamento(avistamento, usuarioId, petId);
                count++;
            }
        }

        System.out.println("👀 Avistamentos carregados: " + count);
    }
}
