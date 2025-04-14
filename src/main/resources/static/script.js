// Inicializa os ícones do Lucide (biblioteca de ícones)
lucide.createIcons();

// Variável que controla se estamos na tela de login (true) ou registro (false)
let isLogin = true;

// URL base da API - substitua pela URL do seu backend
const API_URL = 'http://localhost:8080/usuarios';

// Função para fazer login
async function fazerLogin(email, senha) {
    try {
        const response = await fetch(`${API_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, senha }),
        });

        // Verifica se a resposta não foi OK
        if (!response.ok) {
            // Captura o corpo da resposta como texto (ex: "Senha incorreta")
            const errorText = await response.text();

            // Lança o erro com a mensagem vinda do back-end
            throw new Error(errorText || 'Falha no login');
        }

        const data = await response.text();

        // Armazena o "token" no localStorage (se for realmente um token)
        localStorage.setItem('token', data); // aqui é 'data', não 'data.token', porque você usou response.text()

        // Redireciona para a dashboard
        window.location.href = '/dashboard';

        return data;
    } catch (error) {
        console.error('Erro no login:', error.message);
        alert(error.message); // mostra "Senha incorreta" se for o erro real vindo do back
    }

}

// Função para fazer registro
async function fazerRegistro(nome, telefone, email, senha) {
    try {
        const response = await fetch(`${API_URL}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ nome, telefone, email, senha }),
        });

        if (!response.ok) {
            // Captura o corpo da resposta como texto (ex: "Senha incorreta")
            const errorText = await response.text();
            // Lança o erro com a mensagem vinda do back-end
            throw new Error(errorText || 'Falha no cadastro');
        }

        const data = await response.text();
        alert('Registro realizado com sucesso! Faça login para continuar.');

        // Volta para a tela de login após registro bem-sucedido
        toggleForm();

        return data;
    } catch (error) {
        console.error('Erro no login:', error.message);
        alert(error.message);
    }
}

// Função que alterna entre os formulários de login e registro
function toggleForm() {
    // Inverte o estado atual (se estava em login vai para registro e vice-versa)
    isLogin = !isLogin;

    // Controla a visibilidade dos campos extras do registro (nome e telefone)
    const nameField = document.getElementById('nameField');
    const phoneField = document.getElementById('phoneField');
    const nameInput = nameField.querySelector('input');
    const phoneInput = phoneField.querySelector('input');

    // Se for login, esconde os campos e remove required; se for registro, mostra e adiciona required
    nameField.style.display = isLogin ? 'none' : 'block';
    phoneField.style.display = isLogin ? 'none' : 'block';
    nameInput.required = !isLogin;
    phoneInput.required = !isLogin;

    // Atualiza os textos do formulário dependendo do modo
    document.getElementById('formTitle').textContent = isLogin
        ? 'Bem-vindo de volta!' // Texto para login
        : 'Criar Conta';        // Texto para registro

    document.getElementById('formSubtitle').textContent = isLogin
        ? 'Faça login para acessar sua conta' // Subtítulo para login
        : 'Cadastre-se para começar';         // Subtítulo para registro

    document.getElementById('submitText').textContent = isLogin
        ? 'Entrar'    // Texto do botão para login
        : 'Cadastrar'; // Texto do botão para registro

    document.getElementById('toggleText').textContent = isLogin
        ? 'Não tem uma conta? Cadastre-se' // Link para trocar para registro
        : 'Já tem uma conta? Entre';       // Link para trocar para login

    // Atualiza o ícone do botão de envio
    const submitIcon = document.getElementById('submitIcon');
    // Troca entre ícone de login e registro
    submitIcon.setAttribute('data-lucide', isLogin ? 'log-in' : 'user-plus');
    // Recria os ícones para atualizar
    lucide.createIcons();

    // Limpa todos os campos do formulário
    document.getElementById('authForm').reset();
}

// Função que lida com o envio do formulário
async function handleSubmit(event) {
    // Previne o comportamento padrão de envio do formulário
    event.preventDefault();

    // Coleta todos os dados do formulário
    const formData = new FormData(event.target);
    const data = Object.fromEntries(formData.entries());

    try {
        if (isLogin) {
            // Se for login, envia apenas email e senha
            await fazerLogin(data.email, data.password);
        } else {
            // Se for registro, envia todos os dados
            await fazerRegistro(data.name, data.phone, data.email, data.password);
        }
    } catch (error) {
        console.error('Erro ao processar formulário:', error);
        alert('Ocorreu um erro. Por favor, tente novamente.');
    }
}