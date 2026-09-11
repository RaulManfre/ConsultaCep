# DOCUMENTAÇÃO - ConsultaCep

## 📋 Índice
1. [Visão Geral](#visão-geral)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Estrutura do Projeto](#estrutura-do-projeto)
4. [Arquitetura](#arquitetura)
5. [Como Executar](#como-executar)
6. [Funcionalidades](#funcionalidades)
7. [Guia de Uso](#guia-de-uso)
8. [Estrutura de Código](#estrutura-de-código)

---

## 🎯 Visão Geral

**ConsultaCep** é uma aplicação Android desenvolvida em **Kotlin** que permite aos usuários consultar informações de endereços brasileiros através do número de CEP (Código de Endereçamento Postal).

A aplicação integra-se com a API **ViaCEP** para buscar dados de localização em tempo real, oferecendo uma interface simples e intuitiva para que os usuários obtenham informações de rua, bairro, município e UF através de apenas 8 dígitos do CEP.

**Público-alvo:** Aplicação educacional desenvolvida por alunos da ETEC (Escola Técnica Estadual) para fins de aprendizado de desenvolvimento Android.

---

## 🛠️ Tecnologias Utilizadas

### Linguagem
- **Kotlin** - 100% do código fonte

### Framework & Runtime
- **Android 11+** (API 36 - Target SDK)
- **Android Gradle Plugin 8.x**
- **Java 11** (Compatibilidade de compilação)

### Dependências Principais

| Biblioteca | Versão | Propósito |
|-----------|--------|----------|
| **Retrofit** | 2.11.0 | Cliente HTTP para consumo de APIs REST |
| **Gson Converter** | 2.11.0 | Serialização/Desserialização JSON |
| **AndroidX Core** | Latest | Funcionalidades core do Android moderno |
| **AndroidX AppCompat** | Latest | Compatibilidade com versões antigas |
| **Material Design** | Latest | Componentes UI do Material Design 3 |
| **ConstraintLayout** | Latest | Layout responsivo para telas |
| **JUnit** | Latest | Testes unitários |
| **Espresso** | Latest | Testes de UI (AndroidTest) |

---

## 📁 Estrutura do Projeto

```
ConsultaCep/
│
├── app/                                          # Módulo principal da aplicação
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/sp/etec/appconsultacep/
│   │   │   │   ├── MainActivity.kt              # Activity principal
│   │   │   │   ├── api/
│   │   │   │   │   ├── ViaCepClient.kt         # Cliente Retrofit (Singleton)
│   │   │   │   │   └── ViaCepService.kt        # Interface de requisições HTTP
│   │   │   │   └── model/
│   │   │   │       └── ResponseEndereco.kt     # Data class para resposta da API
│   │   │   ├── res/                            # Recursos da aplicação
│   │   │   │   ├── layout/                     # Arquivos XML de interface
│   │   │   │   ├── drawable/                   # Imagens e drawable resources
│   │   │   │   ├── values/                     # Strings, cores, estilos
│   │   │   │   ├── values-night/               # Recursos para modo noturno
│   │   │   │   ├── mipmap-*/                   # Ícones para diferentes densidades
│   │   │   │   └── xml/                        # Configurações XML
│   │   │   └── AndroidManifest.xml             # Manifest da aplicação
│   │   ├── test/                               # Testes unitários
│   │   └── androidTest/                        # Testes instrumentados
│   ├── build.gradle.kts                         # Build script do app
│   └── proguard-rules.pro                       # Regras de ofuscação
│
├── build.gradle.kts                             # Build script raiz
├── settings.gradle.kts                          # Configuração de módulos
├── gradle.properties                            # Propriedades Gradle
├── gradlew                                      # Gradle Wrapper (Linux/Mac)
├── gradlew.bat                                  # Gradle Wrapper (Windows)
└── .gitignore                                   # Arquivos ignorados pelo Git
```

---

## 🏗️ Arquitetura

### Padrão Arquitetural: MVVM + Client-Service

A aplicação segue o padrão **MVVM (Model-View-ViewModel)** simplificado, mais adequado para aplicações pequenas e educacionais:

```
┌─────────────────────────────────────────────────┐
│                  MainActivity                    │
│              (View - Interface UI)              │
└──────────────────┬──────────────────────────────┘
                   │ (Coroutines/Lifecycle)
                   ↓
┌─────────────────────────────────────────────────┐
│            ViaCepClient (Singleton)             │
│      (Fornecedor da instância do Retrofit)      │
└──────────────────┬──────────────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────────────┐
│          ViaCepService (Interface)              │
│    (Definição de endpoints da API ViaCEP)       │
└──────────────────┬──────────────────────────────┘
                   │ (HTTP Request)
                   ↓
┌─────────────────────────────────────────────────┐
│        API ViaCEP (https://viacep.com.br/)     │
└──────────────────┬──────────────────────────────┘
                   │ (JSON Response)
                   ↓
┌─────────────────────────────────────────────────┐
│         ResponseEndereco (Data Class)           │
│         (Model - Dados de Endereço)             │
└─────────────────────────────────────────────────┘
```

### Fluxo de Dados

1. **Usuário insere CEP** → `MainActivity` captura o valor do `EditText`
2. **Validação** → Verifica se o CEP tem exatamente 8 dígitos
3. **Requisição Assíncrona** → `lifecycleScope.launch` executa a busca em thread separada
4. **Consumo da API** → `ViaCepClient.instance.buscarEndereco(cep)` (Retrofit/Coroutine)
5. **Desserialização** → Gson converte JSON para objeto `ResponseEndereco`
6. **Atualização da UI** → `TextView` é atualizada com o logradouro

---

## 🚀 Como Executar

### Pré-requisitos
- **Android Studio** (Versão Flamingo ou superior)
- **Android SDK 36** instalado
- **Gradle 8.x** (incluído no projeto)
- **Conexão com Internet** (necessária para consultar a API ViaCEP)

### Passos para Execução

#### 1. Clonar o Repositório
```bash
git clone https://github.com/RaulManfre/ConsultaCep.git
cd ConsultaCep
```

#### 2. Abrir no Android Studio
- Abra o Android Studio
- Selecione **File → Open**
- Navegue até o diretório do projeto
- Clique em **Open**

#### 3. Sincronizar Gradle
- Android Studio pedirá para sincronizar as dependências
- Clique em **Sync Now**
- Aguarde a conclusão do download das bibliotecas

#### 4. Configurar Emulador ou Dispositivo
- Abra o **AVD Manager** (Emulator icon na toolbar)
- Crie/Selecione um emulador com Android 11+
- Ou conecte um dispositivo Android via USB

#### 5. Executar a Aplicação
- Pressione `Shift + F10` ou clique no botão **Run** (ícone play verde)
- Selecione o emulador/dispositivo
- Aguarde a compilação e instalação

#### 6. Usar a Aplicação
- A aplicação abrirá com uma interface contendo:
  - Campo de entrada para CEP (8 dígitos)
  - Botão "Consultar"
  - Campo de saída mostrando o logradouro

---

## ✨ Funcionalidades

### Funcionalidade Principal: Consulta de CEP

A aplicação permite:

1. **Entrada de CEP**
   - Input validado para 8 dígitos numéricos
   - Feedback de erro para CEP inválido

2. **Busca de Endereço**
   - Consulta a API ViaCEP em tempo real
   - Execução assíncrona sem travamento da UI

3. **Exibição de Dados**
   - Mostra o logradouro (nome da rua/avenida)
   - Arquitetura preparada para exibir mais dados (bairro, cidade, UF, DDD)

### Campos Disponíveis (ResponseEndereco)
- `logradouro` - Nome da rua, avenida, etc.
- `bairro` - Bairro
- `uf` - Unidade Federativa (Estado)
- `localidade` - Município/Cidade
- `ddd` - Código de Discagem Direta (Telefone)

---

## 👤 Guia de Uso

### Interface da Aplicação

```
┌─────────────────────────────────────┐
│       App Consulta CEP              │
├─────────────────────────────────────┤
│                                     │
│  Informe o CEP (8 dígitos):         │
│  ┌─────────────────────────────────┐
│  │ [            01310-100          ]│
│  └─────────────────────────────────┘
│                                     │
│  ┌──────────────────────────────────┐
│  │   CONSULTAR                      │
│  └──────────────────────────────────┘
│                                     │
│  Logradouro:                        │
│  ┌─────────────────────────────────┐
│  │ Avenida Paulista                 │
│  └─────────────────────────────────┘
│                                     │
└─────────────────────────────────────┘
```

### Passo a Passo

1. **Abrir a Aplicação**
   - Após instalação, toque no ícone do app

2. **Inserir CEP**
   - Clique no campo "Informe o CEP"
   - Insira 8 dígitos (apenas números)
   - Ex: `01310100`

3. **Consultar**
   - Clique no botão "CONSULTAR"
   - Aguarde a resposta (alguns segundos)

4. **Visualizar Resultado**
   - O logradouro aparecerá no campo "Logradouro"
   - Se houver erro, uma notificação aparecerá

### Validação de Entrada
- ❌ CEP com menos de 8 dígitos → Exibe "CEP INVÁLIDO"
- ❌ CEP com letras → Será ignorado (input restringido)
- ✅ CEP com 8 dígitos → Executa a busca

---

## 💻 Estrutura de Código

### 1. MainActivity.kt - A View Principal

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicialização da Activity
        // Setup da UI
        // Listeners de clique
    }
}
```

**Responsabilidades:**
- Inicializar componentes de UI
- Capturar entrada do usuário
- Chamar a API via ViaCepClient
- Atualizar TextView com resultado
- Exibir mensagens de erro (Toast)

**Componentes de UI:**
- `EditText` (edtCep) - Campo de entrada
- `Button` (btnConsultar) - Botão de busca
- `TextView` (txtLogradouro) - Exibição de resultado

---

### 2. ViaCepClient.kt - Singleton do Retrofit

```kotlin
object ViaCepClient {
    private const val BASE_URL = "https://viacep.com.br/"
    
    val instance: ViaCepService by lazy {
        // Criação lazy da instância Retrofit
    }
}
```

**Responsabilidades:**
- Gerenciar a instância única (Singleton) do Retrofit
- Configurar a URL base da API
- Adicionar conversor JSON (Gson)
- Ser o único ponto de criação de requisições

**Padrão `lazy`:**
- A instância só é criada quando acessada pela primeira vez
- Economiza recursos se nunca for usado

---

### 3. ViaCepService.kt - Interface do Retrofit

```kotlin
interface ViaCepService {
    @GET("/ws/{cep}/json/")
    suspend fun buscarEndereco(@Path("cep") cep: String): ResponseEndereco
}
```

**Responsabilidades:**
- Definir o endpoint HTTP da API
- Especificar o método (GET)
- Mapear parâmetros (Path, Query, Body)
- Retornar o tipo esperado

**Anotações:**
- `@GET` - Método HTTP GET
- `@Path` - Parâmetro na URL
- `suspend` - Função Coroutine (não-bloqueante)

---

### 4. ResponseEndereco.kt - Data Class

```kotlin
data class ResponseEndereco(
    val logradouro: String,
    val bairro: String,
    val uf: String,
    val localidade: String,
    val ddd: String
)
```

**Responsabilidades:**
- Mapear a resposta JSON da API
- Fornecer acesso estruturado aos dados
- Ser automaticamente desserializado pelo Gson

**Data Class:**
- Gera automaticamente `equals()`, `hashCode()`, `toString()`
- Fornece destructuring assignments
- Imutável e segura

---

## 🔌 API ViaCEP

### Informações da API

**Endpoint:**
```
GET https://viacep.com.br/ws/{CEP}/json/
```

**Exemplo de Requisição:**
```
https://viacep.com.br/ws/01310100/json/
```

**Exemplo de Resposta (JSON):**
```json
{
  "cep": "01310-100",
  "logradouro": "Avenida Paulista",
  "complemento": "lado ímpar",
  "bairro": "Bela Vista",
  "localidade": "São Paulo",
  "uf": "SP",
  "ibge": "3550308",
  "gia": "",
  "ddd": "11",
  "siafi": "7107"
}
```

### Tratamento de Erros
- API retorna erro se CEP for inválido
- App deveria tratar exceções (melhorias futuras)

---

## 📚 Dependências Gradle

### Build Configuration

```kotlin
// app/build.gradle.kts
android {
    namespace = "br.sp.etec.appconsultacep"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // ... outras dependências
}
```

---

## 🔐 Permissões Android

### AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

A aplicação requer acesso à internet para:
- Conectar à API ViaCEP
- Enviar e receber dados

---

## 🚀 Melhorias Futuras

1. **Tratamento de Erros Robusto**
   - Try-catch para exceções de rede
   - Mensagens de erro específicas

2. **Mostrar Mais Informações**
   - Bairro, Cidade, UF, DDD
   - Layout expandido

3. **Histórico de Consultas**
   - Salvar CEPs consultados
   - Banco de dados local (Room)

4. **Modo Offline**
   - Cache de resultados anteriores
   - Sincronização quando conectar

5. **Validação de Entrada Avançada**
   - Máscara de formatação (12345-678)
   - Sugestão automática

6. **Testes Automatizados**
   - Testes unitários (JUnit)
   - Testes instrumentados (Espresso)

7. **Internacionalização**
   - Suporte a múltiplos idiomas
   - Strings em arquivo resources

---

## 📝 Notas Técnicas

### Coroutines
- Usado `lifecycleScope.launch` para operações assíncronas
- Garante que a requisição seja cancelada se a Activity for destruída
- Evita memory leaks

### Singleton Pattern
- `object ViaCepClient` implementa Singleton automaticamente
- Única instância de Retrofit durante toda a execução

### Retrofit + Gson
- Retrofit converte automaticamente JSON ↔ Kotlin Objects
- Gson cuida da serialização/desserialização
- Type-safe e com bom desempenho

---

## 📞 Contato e Suporte

**Desenvolvedor:** Raul Manfredini
**Repositório:** https://github.com/RaulManfre/ConsultaCep
**Instituição:** ETEC (Escola Técnica Estadual)

---

**Versão da Documentação:** 1.0  
**Data:** Setembro de 2026  
**Status:** ✅ Completo
