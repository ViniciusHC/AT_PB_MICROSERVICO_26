import { useState, useEffect } from "react";
import "./Forum.css";
import { useNavigate } from "react-router-dom";

export type TopicoStatus = 'PENDENTE' | 'ACEITO' | 'RECUSADO';

export interface Forum {
    id?: number;
    idUsuario: number;
    titulo: string;
    titutloJogo: string;
    texto: string;
    dataCriacao?: string;
    status?: TopicoStatus;
    idJogo?: number | null;
    motivoErro?: string | null;
}

function ForumPage() {

    const navigate = useNavigate();

    const [topico, setTopico] = useState<{
        idUsuario: number | string;
        titulo: string;
        titutloJogo: string;
        texto: string;
    }>({
        idUsuario: '',
        titulo: '',
        titutloJogo: '',
        texto: ''
    });

    const [topicos, setTopicos] = useState<Forum[]>([]);
    const [carregando, setCarregando] = useState<boolean>(false);

    function limparFormulario() {
        setTopico({
            idUsuario: '',
            titulo: '',
            titutloJogo: '',
            texto: ''
        });
    }

    async function listarTopicos() {
        setCarregando(true);
        try {
            const resposta = await fetch("http://localhost:8085/forum-service/topicos");
            if (!resposta.ok) {
                alert(`Erro ao buscar tópicos: Servidor retornou status ${resposta.status}`);
                return;
            }
            const data: Forum[] = await resposta.json();
            setTopicos(data);
        } catch (erro) {
            console.error("Erro ao listar topicos:", erro);
            alert("Erro de conexão ao buscar os topicos.");
        } finally {
            setCarregando(false);
        }
    }

    async function criarTopico() {
        if (!topico.titulo || !topico.texto || !topico.titutloJogo || topico.idUsuario === '') {
            alert("Por favor, preencha todos os campos do tópico.");
            return;
        }

        const novoTopico: Forum = {
            idUsuario: Number(topico.idUsuario),
            titulo: topico.titulo,
            titutloJogo: topico.titutloJogo,
            texto: topico.texto
        };

        try {
            const resposta = await fetch("http://localhost:8085/forum-service/topicos", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(novoTopico)
            });
            if (resposta.ok) {
                alert("Tópico cadastrado com sucesso! A validação do jogo foi enviada via RabbitMQ.");
                limparFormulario();
                listarTopicos();
            } else {
                alert("Erro ao cadastrar o Tópico no servidor.");
            }
        } catch (erro) {
            console.error("Erro ao adicionar Tópico:", erro);
            alert("Erro de conexão ao cadastrar o tópico.");
        }
    }

    async function deletarTopico(topico: Forum) {
        if (!topico.id) return;
        try {
            const resposta = await fetch(`http://localhost:8085/forum-service/topicos/${topico.id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (resposta.ok) {
                alert("Tópico excluído com sucesso!");
                listarTopicos();
            } else {
                alert("Erro ao excluir o tópico do servidor.");
            }
        } catch (erro) {
            console.error("Erro ao deletar tópico:", erro);
            alert("Erro de conexão ao excluir o tópico.");
        }
    }

    function atualizarStatus(topico: Forum) {
        switch (topico.status) {
            case 'ACEITO':
                return <span className="badge badge-aceito">Aceito / Confirmado</span>;
            case 'RECUSADO':
                return (
                    <button
                        type="button"
                        className="badge badge-recusado btn-erro-alert"
                        style={{ cursor: 'pointer' }}
                        onClick={() => alert(`Erro no tópico "${topico.titulo}": ${topico.motivoErro || 'Tópico recusado pelo sistema'}`)}
                    >
                        Recusado (Ver Erro)
                    </button>
                );
            case 'PENDENTE':
            default:
                return <span className="badge badge-pendente">Pendente</span>;
        }
    }

    useEffect(() => {
        listarTopicos();
    }, [])

    return (
        <div>
            <h1>Fórum sobre Jogos</h1>
            <div className="acoes-navegacao">
                <button type="button" onClick={() => navigate("/")}>Voltar para página principal</button>
                <button type="button" className="btn-atualizar" onClick={listarTopicos}>
                    {carregando ? "Atualizando..." : "Atualizar Tópicos "}
                </button>
            </div>
            <div className="container">
                <h2>Cadastrar novo Tópico</h2>
                <form>
                    <label>ID do Usuário:</label>
                    <input
                        type="number"
                        value={topico.idUsuario}
                        className='input'
                        placeholder='Ex: 1'
                        onChange={(e) => setTopico({ ...topico, idUsuario: e.target.value })}
                    />
                    <label>Título do Tópico:</label>
                    <input
                        value={topico.titulo}
                        className='input'
                        placeholder='Título do Tópico'
                        onChange={(e) => setTopico({ ...topico, titulo: e.target.value })}
                    />
                    <label>Título do Jogo:</label>
                    <input
                        value={topico.titutloJogo}
                        className='input'
                        placeholder='Nome exato do Boardgame associado'
                        onChange={(e) => setTopico({ ...topico, titutloJogo: e.target.value })}
                    />
                    <label>Conteúdo:</label>
                    <textarea
                        value={topico.texto}
                        className='input'
                        placeholder='Resumo ou mensagem do tópico'
                        rows={4}
                        onChange={(e) => setTopico({ ...topico, texto: e.target.value })}
                    />
                    <button type="button" onClick={criarTopico}>Cadastrar Tópico</button>
                </form>

                <h2>Tópicos</h2>
                <div className='lista-topicos'>
                    {topicos.length === 0 && !carregando && (
                        <p className="nenhum-topico">Nenhum tópico encontrado.</p>
                    )}
                    {topicos.map((item) => (
                        <div className={`card-topico ${item.status ? `card-${item.status.toLowerCase()}` : ''}`} key={item.id}>
                            <div className="topico-cabecalho">
                                <h3>{item.titulo}</h3>
                                {atualizarStatus(item)}
                            </div>

                            <div className="topico-info-jogo">
                                <strong>Jogo:</strong> {item.titutloJogo || "Não informado"}
                                {item.idJogo && (
                                    <span className="info-id-jogo"> (ID Jogo: {item.idJogo})</span>
                                )}
                            </div>

                            <p className="topico-texto">{item.texto}</p>

                            <div className="topico-rodape">
                                <span className="topico-metadados">
                                    Autor ID: {item.idUsuario} {item.dataCriacao ? `• Criado em: ${new Date(item.dataCriacao).toLocaleString('pt-BR')}` : ''}
                                </span>
                                <button type="button" className="btn-excluir" onClick={() => deletarTopico(item)}>Excluir Tópico</button>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export default ForumPage;