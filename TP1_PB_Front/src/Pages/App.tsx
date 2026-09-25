import { useEffect, useState } from 'react';
import './App.css'
import { useNavigate } from 'react-router-dom';


interface BoardGame {
  id?: number;
  nome: string;
  descricao: string;
  editora: string;
  tipo: string;
  dataLancamento: string;
}

function App() {

  const [boardgames, setBoardGames] = useState<BoardGame[]>([]);
  const [jogo, setJogo] = useState({
    nome: '',
    descricao: '',
    editora: '',
    tipo: '',
    dataLancamento: ''
  });

  const [buscador, setBuscador] = useState('');

  function limparFormulario() {
    setJogo({
      nome: '',
      tipo: '',
      editora: '',
      dataLancamento: '',
      descricao: ''
    });
  }

  async function listarJogos() {
    try {
      const resposta = await fetch("http://localhost:8085/boardgame-service/boardgames");
      if (!resposta.ok) {
        alert(`Erro ao buscar os jogos: Servidor retornou status ${resposta.status}`);
        return;
      }
      const data = await resposta.json();
      setBoardGames(data);
    } catch (erro) {
      console.error("Erro ao listar jogos:", erro);
      alert("Erro de conexão ao buscar os jogos.");
    }
  }

  async function adicionarJogo(jogo: BoardGame) {
    try {
      const resposta = await fetch("http://localhost:8085/boardgame-service/boardgames", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(jogo)
      });
      if (resposta.ok) {
        alert("Jogo cadastrado com sucesso!");
        limparFormulario();
        listarJogos();
      } else {
        alert("Erro ao cadastrar o jogo no servidor.");
      }
    } catch (erro) {
      console.error("Erro ao adicionar jogo:", erro);
      alert("Erro de conexão ao cadastrar o jogo.");
    }
  }

  async function alterarJogo(jogo: BoardGame) {
    try {
      const resposta = await fetch(`http://localhost:8085/boardgame-service/boardgames/${jogo.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(jogo)
      });
      if (resposta.ok) {
        alert("Jogo alterado com sucesso!");
        limparFormulario();
        listarJogos();
      } else {
        alert("Erro ao alterar o jogo no servidor.");
      }
    } catch (erro) {
      console.error("Erro ao alterar jogo:", erro);
      alert("Erro de conexão ao alterar o jogo.");
    }
  }

  async function deletarJogo(jogo: BoardGame) {
    try {
      const resposta = await fetch(`http://localhost:8085/boardgame-service/boardgames/${jogo.id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (resposta.ok) {
        alert("Jogo excluído com sucesso!");
        listarJogos();
      } else {
        alert("Erro ao excluir o jogo no servidor.");
      }
    } catch (erro) {
      console.error("Erro ao deletar jogo:", erro);
      alert("Erro de conexão ao excluir o jogo.");
    }
  }

  async function listarJogo(jogoBuscado: BoardGame) {
    try {
      const resposta = await fetch(`http://localhost:8085/boardgame-service/boardgames/${jogoBuscado.id}`);
      if (!resposta.ok) {
        alert(`Erro ao buscar dados do jogo: Servidor retornou status ${resposta.status}`);
        return;
      }
      const data = await resposta.json();
      setBoardGames([data]);
    } catch (erro) {
      console.error("Erro ao buscar jogo:", erro);
      alert("Erro de conexão ao buscar o jogo.");
    }
  }

  function cadastrarOuAlterarJogo(jogo: BoardGame) {
    if (jogo.id) {
      alterarJogo(jogo);
    } else {
      adicionarJogo(jogo);
    }
  }

  function buscarJogo(nome: String) {
    const jogoProcurado = boardgames.find((boardgame) => boardgame.nome.toLocaleLowerCase() === nome.toLocaleLowerCase());
    if (jogoProcurado) {
      listarJogo(jogoProcurado);
      setBuscador('');
    } else {
      alert("Jogo não encontrado!");
    }
  }

  useEffect(() => {
    listarJogos();
  }, [])

  const navigate = useNavigate();

  return (
    <div>
      <h1>Catálogo de jogos</h1>
      <div className='botao_forum'>
        <button type="button" onClick={() => navigate("/forum")}>Ir para forum</button>
      </div>
      <div className='container'>
        <form>
          <input
            value={jogo?.nome}
            className={'input'}
            placeholder='Nome do Produto'
            onChange={(e) => setJogo({ ...jogo, nome: e.target.value })}
          />
          <input
            value={jogo.tipo}
            className='input'
            placeholder='Tipo (Ex: Euro, Ameri, Card Game)'
            onChange={(e) => setJogo({ ...jogo, tipo: e.target.value })}
          />
          <input
            value={jogo.editora}
            className='input'
            placeholder='Editora'
            onChange={(e) => setJogo({ ...jogo, editora: e.target.value })}
          />
          <div>
            <label>Data de Lançamento:</label>
            <input
              type="date"
              value={jogo.dataLancamento}
              className='input'
              onChange={(e) => setJogo({ ...jogo, dataLancamento: e.target.value })}
            />
          </div>
          <textarea
            value={jogo.descricao}
            className='input'
            placeholder='Descrição do jogo'
            rows={4}
            onChange={(e) => setJogo({ ...jogo, descricao: e.target.value })}
          />
          <button type="button" onClick={() => { cadastrarOuAlterarJogo(jogo) }}>Cadastrar Jogo</button>
        </form>
      </div>
      <form className='buscadorDeJogos'>
        <input
          value={buscador}
          className='buscador'
          placeholder='Digite o nome do Jogo'
          onChange={(e) => setBuscador(e.target.value)}
        />
        <button type='button' onClick={() => buscarJogo(buscador)}>Buscar Jogo</button>
        <button type='button' onClick={() => listarJogos()}>Listar todos</button>
      </form>

      <div className='lista-jogos'>
        {boardgames.map((boardgame) => (
          <div className='card-jogo' key={boardgame.id}>
            <p>{boardgame.nome}</p>
            <button type="button" onClick={() => setJogo(boardgame)}>Alterar Jogo</button>
            <button type="button" onClick={() => deletarJogo(boardgame)}>Excluir Jogo</button>
          </div>
        ))
        }
      </div>
    </div>
  )
}

export default App;
