document.addEventListener("DOMContentLoaded", () => {
    // ====================
    // Página de Cadastro
    // ====================
    const btnCadastro = document.getElementById("btnCadastro");
    if (btnCadastro) {
        const Inome = document.querySelector(".nome");
        const Iemail = document.querySelector(".email");
        const Isenha = document.querySelector(".senha");
        const Itelefone = document.querySelector(".telefone");

        btnCadastro.addEventListener("click", () => {
            if (!Inome.value || !Iemail.value || !Isenha.value || !Itelefone.value) {
                alert("Preencha todos os campos.");
                return;
            }

            fetch("http://localhost:8080/usuarios", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    nome: Inome.value,
                    email: Iemail.value,
                    senha: Isenha.value,
                    telefone: Itelefone.value
                })
            })
                .then(res => {
                    if (res.ok) {
                        alert("Cadastro realizado com sucesso!");
                        window.location.href = "login.html";
                    } else {
                        alert("Erro ao cadastrar.");
                    }
                })
                .catch(err => {
                    console.error(err);
                    alert("Erro ao conectar com o servidor.");
                });
        });
    }

    // ====================
    // Página de Login
    // ====================
    const btnLogin = document.getElementById("btnLogin");
    if (btnLogin) {
        const Iemail = document.querySelector(".email");
        const Isenha = document.querySelector(".senha");

        btnLogin.addEventListener("click", async () => {
            if (!Iemail.value || !Isenha.value) {
                alert("Preencha o e-mail e a senha.");
                return;
            }

            try {
                const response = await fetch("http://localhost:8080/usuarios/login", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        email: Iemail.value,
                        senha: Isenha.value
                    })
                });

                const data = await response.text();

                if (response.ok) {
                    alert("Login realizado com sucesso!");
                    // window.location.href = "dashboard.html";
                } else {
                    alert(data || "Erro ao fazer login.");
                }
            } catch (error) {
                console.error(error);
                alert("Erro ao conectar com o servidor.");
            }
        });
    }
});
