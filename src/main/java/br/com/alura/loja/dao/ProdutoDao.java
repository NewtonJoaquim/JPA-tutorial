package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
		return this.em.createNamedQuery("Produto.produtosPorCategoria",Produto.class)
				.setParameter("nome", name)
				.getResultList();
	}
	
	public BigDecimal buscarPrecoDoProduto(String name) {
		String jpql = "Select p.preco from Produto p where p.nome=:nome";
		return this.em.createQuery(jpql,BigDecimal.class)
				.setParameter("nome", name)
				.getSingleResult();
	}
	
	public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		Predicate filtros = criteriaBuilder.and();
		
		if(nome != null && !nome.trim().isEmpty()) {
			filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("nome"), nome));
		}
		if(preco != null) {
			filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("preco"), preco));
		}
		if(dataCadastro != null) {
			filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("dataCadastro"), dataCadastro));
		}
		query.where(filtros);
		
		return em.createQuery(query).getResultList();
	}
}
