package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Cliente;
import entities.ItemVenda;
import entities.Sistema;

public class Program {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		Sistema sistema = new Sistema();
//		ListaDeProdutos produtos = new ListaDeProdutos();
//		ListaDeClientes clientes = new ListaDeClientes();

		sistema.adicionarProduto(1234, "Mi 8 lite", "descricao do produto 1", 999.90, 1499.99, 50, "Celular", "Xiaomi");
		sistema.adicionarProduto(1235, "Redmi note 12", "descricao do produto 2", 1999.90, 2499.95, 20, "Tablet",
				"Xiaomi");
		sistema.adicionarProduto(1236, "Galaxy S23", "descricao do produto 3", 2999.90, 3499.99, 4, "Celular",
				"Samsung");

		sistema.adicionarCliente(
				new Cliente("Rodrigo Garcia", "Rua Almir Nelson de Almeida, 290, bloco 6 apto 5 - Curitiba - PR",
						"(41) 99866-6332", "rodrigosrising@gmail.com"));
		sistema.adicionarCliente(
				new Cliente("Camila Sartori", "Rua Almir Nelson de Almeida, 290, bloco 6 apto 5 - Curitiba - PR",
						"(41) 99633-9225", "camylla55@gmail.com"));

		System.out.println("Todos os clientes");
		sistema.listarClientes();
		System.out.println();

		System.out.println("Todos os produtos");
		sistema.listarProdutos();
		System.out.println();

		System.out.println("Produtos com estoque abaixo do mínimo");
		sistema.listarProdutosEmFalta();
		System.out.println();

		System.out.println("Todos os fornecedores");
		sistema.listarFornecedores();
		System.out.println();

		System.out.println("Todos as categorias");
		sistema.listarCategorias();
		System.out.println();

		// Obtendo os clientes com IDs 1 e 2
		Cliente cliente1 = sistema.buscarClientePorId(1);
		Cliente cliente2 = sistema.buscarClientePorId(2);

		// Criando uma lista de itens de venda (um item para o produto com quantidade 4)
		List<ItemVenda> itens = new ArrayList<>();
		itens.add(new ItemVenda(sistema.buscarProdutoPorSku(1236), 1));
		itens.add(new ItemVenda(sistema.buscarProdutoPorSku(1235), 2));

		// Realizando a venda para o cliente 1
		sistema.realizarVenda(cliente1, itens);

		// Tentando realizar a mesma venda para o cliente 2 (a venda deve falhar pois
		// não há estoque suficiente)
		sistema.realizarVenda(cliente2, itens);

		// sistema.mostrarVendasRealizadas();
		sistema.listarVendas();

		System.out.println("Todos os produtos");
		sistema.listarProdutos();
		System.out.println();

//		int opc = 2;
//		
//		switch(opc) {
//			case 1: {
//				System.out.println("Cadastrar Produto");
//				System.out.println();
//				break;
//			}
//			case 2: {
//				System.out.println("Listar Produtos");
//				produtos.listarProdutos();
//				System.out.println();
//				break;
//			}
//			case 3: {
//				System.out.println("Editar Produto");
//				System.out.println();
//				break;
//			}
//			case 4: {
//				System.out.println("Remover Produto");
//				//produtos.removerProduto(sku);
//				System.out.println();
//				break;
//			}
//			case 5: {
//				System.out.println("Listar Categorias");
//				System.out.println();
//				break;
//			}
//			case 6: {
//				System.out.println("Listar Fornecedores");
//				System.out.println();
//				break;
//			}
//			case 7: {
//				System.out.println("Cadastrar Cliente");
//				System.out.println();
//				break;
//			}
//			case 8: {
//				System.out.println("Listar Cliente");
//				System.out.println();
//				break;
//			}
//		}

//		System.out.println("Quantos produtos deseja cadastrar? ");
//		int num = scan.nextInt();
//		System.out.println();
//
//		for (int i = 1; i <= num; i++) {
//			System.out.println("Cadastre um produto: ");
//			System.out.println();
//
//			System.out.print("SKU do produto: ");
//			int sku = scan.nextInt();
//			scan.nextLine();
//			System.out.print("Nome do produto: ");
//			String nome = scan.nextLine();
//			System.out.print("Descrição do produto: ");
//			String descricao = scan.nextLine();
//			System.out.print("Preço de compra do produto: ");
//			double valorCompra = scan.nextDouble();
//			System.out.print("Preço de venda do produto: ");
//			double valorVenda = scan.nextDouble();
//			System.out.print("Quantidade em estoque: ");
//			int estoque = scan.nextInt();
//			scan.nextLine();
//			System.out.print("Categoria: ");
//			String cat = scan.nextLine();
//			System.out.print("fornecedor: ");
//			String forn = scan.nextLine();
//
//			Categoria categoria = new Categoria(cat);
//			Fornecedor fornecedor = new Fornecedor(forn);
//
//			produtos.cadastrarProduto(new Produto(sku, nome, descricao, valorCompra, valorVenda, estoque, categoria, fornecedor));
//		}
//		

		scan.close();

	}

}
