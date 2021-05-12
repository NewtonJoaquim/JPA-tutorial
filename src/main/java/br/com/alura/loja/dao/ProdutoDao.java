package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}

	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}

	public void remover(Produto produto) {
		produto = em.merge(produto);
		this.em.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return this.em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos() {
		String jpql = "Select p from Produto p";
		return this.em.createQuery(jpql,Produto.class).getResultList();
	}

	public List<Produto> buscarPorNome(String name) {
		String jpql = "Select p from Produto p where p.nome=:nome";
		return this.em.createQuery(jpql,Produto.class)
				.setParameter("nome", name)
				.getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String name) {
		String jpql = "Select p from Produto p where p.categoria.nome=:nome";
		return this.em.createQuery(jpql,Produto.class)
				.setParameter("nome", name)
				.getResultList();
	}
	
	public BigDecimal buscarPrecoDoProduto(String name) {
		String jpql = "Select p.preco from Produto p where p.nome=:nome";
		return this.em.createQuery(jpql,BigDecimal.class)
				.setParameter("nome", name)
				.getSingleResult();
	}
}
