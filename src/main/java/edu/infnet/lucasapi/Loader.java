package edu.infnet.lucasapi;

import edu.infnet.lucasapi.api.request.AvistamentoRequest;
import edu.infnet.lucasapi.api.request.LocalizacaoRequest;
import edu.infnet.lucasapi.api.request.EnderecoRequest;
import edu.infnet.lucasapi.api.request.PetRequest;
import edu.infnet.lucasapi.api.request.UsuarioRequest;
import edu.infnet.lucasapi.application.service.AvistamentoService;
import edu.infnet.lucasapi.application.service.PetService;
import edu.infnet.lucasapi.application.service.UsuarioService;
import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.enums.TipoLocalizacao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
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
    private final Map<Integer, UsuarioRequest> usuariosMemoria = new ConcurrentHashMap<>();
    private final Map<Integer, PetRequest> petsMemoria = new ConcurrentHashMap<>();
    private final Map<Integer, AvistamentoRequest> avistamentosMemoria = new ConcurrentHashMap<>();
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
        if (!Files.exists(arquivo)) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                UsuarioRequest u = new UsuarioRequest();
                u.setNome(dados[0].trim());
                u.setEmail(dados[1].trim());
                u.setTelefone(dados[2].trim());
                u.setSenha(dados[3].trim());

                int id = idSequence.getAndIncrement();
                usuariosMemoria.put(id, u);
            }
        }

        System.out.printf("👤 Usuários carregados em memória (Feature 1): %d%n", usuariosMemoria.size());
    }

    private void carregarPetsEmMemoria() throws Exception {
        Path arquivo = Path.of("pets.csv");
        if (!Files.exists(arquivo)) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length < 8) continue;

                PetRequest p = new PetRequest();
                p.setNome(dados[0].trim());
                p.setEspecie(dados[1].trim());
                p.setRaca("Desconhecida");
                p.setStatus(dados[4].trim());
                p.setUsuarioId(Long.parseLong(dados[5].trim()));
                p.setIdade(Integer.parseInt(dados[6].trim()));
                p.setDesaparecidoEm(LocalDate.parse(dados[7].trim()));

                int id = idSequence.getAndIncrement();
                petsMemoria.put(id, p);
            }
        }

        System.out.printf("🐶 Pets carregados em memória (Feature 1): %d%n", petsMemoria.size());
    }

    private void carregarAvistamentosEmMemoria() throws Exception {
        Path arquivo = Path.of("avistamentos.csv");
        if (!Files.exists(arquivo)) return;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length < 11) continue;

                EnderecoRequest endereco = new EnderecoRequest();
                endereco.setCep(dados[10].trim());
                endereco.setNumero(dados[6].trim());
                endereco.setComplemento(null);

                LocalizacaoRequest localizacao = new LocalizacaoRequest();
                localizacao.setLatitude(Double.parseDouble(dados[3].trim()));
                localizacao.setLongitude(Double.parseDouble(dados[4].trim()));
                localizacao.setEndereco(endereco);

                AvistamentoRequest a = new AvistamentoRequest();
                a.setDescricao(dados[0].trim());
                a.setPetId(Long.parseLong(dados[1].trim()));
                a.setUsuarioId(Long.parseLong(dados[2].trim()));
                a.setLocalizacao(localizacao);

                int id = idSequence.getAndIncrement();
                avistamentosMemoria.put(id, a);
            }
        }

        System.out.printf("👀 Avistamentos carregados em memória (Feature 1): %d%n", avistamentosMemoria.size());
    }

    private void salvarNoBanco() {
        usuariosMemoria.values().forEach(u -> usuarioService.criar(u.toEntity()));

        petsMemoria.values().forEach(p -> {
            var entidade = p.toEntity();
            entidade.setUsuario(usuarioService.buscarPorId(p.getUsuarioId()));
            petService.criar(entidade);
        });

        avistamentosMemoria.values().forEach(a -> {
            var entidade = a.toEntity();
            entidade.setUsuario(usuarioService.buscarPorId(a.getUsuarioId()));
            entidade.setPet(petService.buscarPorId(a.getPetId()));
            avistamentoService.criarAvistamento(entidade, a.getUsuarioId(), a.getPetId());
        });

        System.out.println("✅ Dados persistidos no banco via Services.");
    }
}
