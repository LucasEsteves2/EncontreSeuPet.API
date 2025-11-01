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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("dev")
@Component
public class Loader implements ApplicationRunner {

    private final UsuarioService usuarioService;
    private final PetService petService;
    private final AvistamentoService avistamentoService;

    // --- Simulação em memória (Feature 1) ---
    private final Map<Integer, Usuario> usuariosMemoria = new ConcurrentHashMap<>();
    private final Map<Integer, Pet> petsMemoria = new ConcurrentHashMap<>();
    private final Map<Integer, Avistamento> avistamentosMemoria = new ConcurrentHashMap<>();
    private final AtomicInteger idSequence = new AtomicInteger(1);

    public Loader(UsuarioService usuarioService, PetService petService, AvistamentoService avistamentoService) {
        this.usuarioService = usuarioService;
        this.petService = petService;
        this.avistamentoService = avistamentoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\n=== 🚀 Iniciando carga de dados em memória (Feature 1) ===");

        carregarUsuariosEmMemoria();
        carregarPetsEmMemoria();
        carregarAvistamentosEmMemoria();

        System.out.println("\n💾 Gravando dados da memória no banco...");
        salvarNoBanco();

        System.out.println("✅ Carga inicial concluída com sucesso!\n");
    }

    private void carregarUsuariosEmMemoria() throws Exception {
        Path arquivo = Path.of("usuarios.csv");


        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Usuario u = Usuario.builder()
                        .nome(dados[0].trim())
                        .email(dados[1].trim())
                        .senha(dados[2].trim())
                        .build();

                int id = idSequence.getAndIncrement();
                usuariosMemoria.put(id, u);
            }
        }

        System.out.printf("👤 Usuários carregados em memória (Feature 1): %d%n", usuariosMemoria.size());
    }

    private void carregarPetsEmMemoria() throws Exception {
        Path arquivo = Path.of("pets.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Long usuarioId = Long.parseLong(dados[5].trim());
                Usuario usuario = usuariosMemoria.get(usuarioId.intValue());
                if (usuario == null) continue;

                Pet p = Pet.builder()
                        .nome(dados[0].trim())
                        .especie(dados[1].trim())
                        .cor(dados[2].trim())
                        .descricao(dados[3].trim())
                        .status(StatusPet.valueOf(dados[4].trim()))
                        .usuario(usuario)
                        .build();

                int id = idSequence.getAndIncrement();
                petsMemoria.put(id, p);
            }
        }

        System.out.printf("🐶 Pets carregados em memória (Feature 1): %d%n", petsMemoria.size());
    }

    private void carregarAvistamentosEmMemoria() throws Exception {
        Path arquivo = Path.of("avistamentos.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Long petId = Long.parseLong(dados[1].trim());
                Long usuarioId = Long.parseLong(dados[2].trim());

                Pet pet = petsMemoria.get(petId.intValue());
                Usuario usuario = usuariosMemoria.get(usuarioId.intValue());
                if (pet == null || usuario == null) continue;

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

                int id = idSequence.getAndIncrement();
                avistamentosMemoria.put(id, avistamento);
            }
        }

        System.out.printf("👀 Avistamentos carregados em memória (Feature 1): %d%n", avistamentosMemoria.size());
    }

    private void salvarNoBanco() {
        usuariosMemoria.values().forEach(usuarioService::criar);
        petsMemoria.values().forEach(petService::criar);
        avistamentosMemoria.values().forEach(a ->
                avistamentoService.criarAvistamento(a, a.getUsuario().getId(), a.getPet().getId())
        );
        System.out.println("Dados persistidos no banco via services.");
    }
}
