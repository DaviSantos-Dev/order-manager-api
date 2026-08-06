# Order Manager 

## Sobre o projeto

Order Manager, é um sistema de gerenciamento de pedidos, projetado para auxiliar pequenos estabelecimentos no gerenciamento de pedidos e produtos.

Este é um projeto em desenvolvimento, com backend em Java e Spring Boot, com arquitetura inspirada em monolito modular com separação em camadas em cada módulo, e princípios de DDD Lite, sendo o sistema orientado ao domínio, e priorizando concentrar as regras de negócios sempre dentro das entidades e usando as classes de service apenas para orquestrar.

Projeto original (CLI): [Order Manager CLI](https://github.com/DaviSantos-Dev/order-manager-cli)

---

## Tecnologias Usadas
- Java 21
- Spring Boot
- PostgreSQL

## Tecnologias Planejadas
- Spring Security (OAuth2 & JWT)
- Teste com JUnit5 & Mockito
- TypeScript
- Angular

## Funcionalidade Implementadas
- Criação, alteração e exclusão de produtos;
- Criação, alteração e exclusão de usuários (Simplificado, será melhorado com a implementação do spring security);
- Criação de pedidos e inativação de pedidos (soft-delete);
- Adição, alteração e remoção de itens de pedidos dentro de pedidos (Sendo item de pedido a aggregate e o pedido uma Aggregate Root).

## Funcionalidades planejadas

- Autenticação de usuários e gerenciamento de autorizações
- Front-end com painéis de pedido (Cliente ou Vendedor)
- Dashboard de Vendedor para visualização de vendas com filtros por data e(ou) produto

## Roadmap

### v1.0 ✅
- CRUD de produtos
- CRUD de usuários
- CRUD de pedidos
- Gerenciamento de itens do pedido

### v1.1 🚧 
- Front-end 
- Dashboard de pedidos

### v1.2 
- Spring Security
- OAuth2 + JWT
- Controle de permissões

### v1.3
- Testes automatizados com JUnit e Mockito
- Melhorias de performance
