// Copyright (c) 2011. This source code is available under the terms of the GNU Lesser General Public License (LGPL)
// Author: Mario Volke <volke@derivo.de>
// derivo GmbH, James-Franck-Ring, 89081 Ulm 

package de.derivo.sparqldlapi.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.derivo.sparqldlapi.Query;
import de.derivo.sparqldlapi.QueryArgument;
import de.derivo.sparqldlapi.QueryAtom;
import de.derivo.sparqldlapi.QueryParser;
import de.derivo.sparqldlapi.QueryToken;
import de.derivo.sparqldlapi.QueryTokenizer;
import de.derivo.sparqldlapi.exceptions.QueryParserException;
import de.derivo.sparqldlapi.impl.QueryParserImpl;
import de.derivo.sparqldlapi.impl.QueryTokenizerImpl;
import de.derivo.sparqldlapi.types.QueryArgumentType;
import de.derivo.sparqldlapi.types.QueryAtomType;
import de.derivo.sparqldlapi.types.QueryType;

/**
 * A jUnit 4.0 test class to test the implementation of QueryParser
 * 
 * @author Mario Volke
 */
public class QueryParserTest 
{
	@Test
	public void testParseSelect()
		throws QueryParserException
	{
		QueryTokenizer tokenizer = new QueryTokenizerImpl();
		List<QueryToken> tokens = tokenizer.tokenize(
			"PREFIX wine: <http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#>\n" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
			"SELECT * WHERE { PropertyValue(?i, wine:hasColor, ?v), PropertyValue(?p, foaf:name, \"foo \\\" bar\") }"
		);
		
		QueryParser parser = new QueryParserImpl();
		Query query = parser.parse(tokens);
		
		Set<QueryArgument> resultVars = new HashSet<QueryArgument>();
		resultVars.add(new QueryArgument(QueryArgumentType.VAR, "i"));
		resultVars.add(new QueryArgument(QueryArgumentType.VAR, "v"));
		resultVars.add(new QueryArgument(QueryArgumentType.VAR, "p"));
		
		List<QueryAtom> atoms = new LinkedList<QueryAtom>();
		atoms.add(new QueryAtom(
			QueryAtomType.PROPERTY_VALUE, 
			new QueryArgument(QueryArgumentType.VAR, "i"),
			new QueryArgument(QueryArgumentType.URI, "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasColor"),
			new QueryArgument(QueryArgumentType.VAR, "v")
		));
		atoms.add(new QueryAtom(
			QueryAtomType.PROPERTY_VALUE, 
			new QueryArgument(QueryArgumentType.VAR, "p"),
			new QueryArgument(QueryArgumentType.URI, "http://xmlns.com/foaf/0.1/name"),
			new QueryArgument(QueryArgumentType.LITERAL, "\"foo \" bar\"^^http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral")
		));
		
		assertEquals(query.getType(), QueryType.SELECT);
		assertEquals(query.getResultVars(), resultVars);
//		assertEquals(query.getAtomGroups().get(0).getAtoms(), atoms);
	}
	
	@Test(expected = QueryParserException.class)
	public void testParseSelectWithException() 
		throws QueryParserException
	{
		QueryTokenizer tokenizer = new QueryTokenizerImpl();
		List<QueryToken> tokens = tokenizer.tokenize(
			"SELECT * WHERE { PropertyValue(?i, wine:hasColor, ?v)"
		);
		
		QueryParser parser = new QueryParserImpl();
		parser.parse(tokens);
	}
	
	@Test
	public void testParseSelectDistinct()
		throws QueryParserException
	{
		QueryTokenizer tokenizer = new QueryTokenizerImpl();
		List<QueryToken> tokens = tokenizer.tokenize(
			"PREFIX wine: <http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#>\n" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
			"SELECT DISTINCT * WHERE { PropertyValue(?i, wine:hasColor, ?v), PropertyValue(?p, foaf:name, \"foo \\\" bar\") }"
		);
		
		QueryParser parser = new QueryParserImpl();
		Query query = parser.parse(tokens);
		
		Set<QueryArgument> resultVars = new HashSet<QueryArgument>();
		resultVars.add(new QueryArgument(QueryArgumentType.VAR, "i"));
		resultVars.add(new QueryArgument(QueryArgumentType.VAR, "v"));
		resultVars.add(new QueryArgument(QueryArgumentType.VAR, "p"));
		
		List<QueryAtom> atoms = new LinkedList<QueryAtom>();
		atoms.add(new QueryAtom(
			QueryAtomType.PROPERTY_VALUE, 
			new QueryArgument(QueryArgumentType.VAR, "i"),
			new QueryArgument(QueryArgumentType.URI, "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasColor"),
			new QueryArgument(QueryArgumentType.VAR, "v")
		));
		atoms.add(new QueryAtom(
			QueryAtomType.PROPERTY_VALUE, 
			new QueryArgument(QueryArgumentType.VAR, "p"),
			new QueryArgument(QueryArgumentType.URI, "http://xmlns.com/foaf/0.1/name"),
			new QueryArgument(QueryArgumentType.LITERAL, "foo \" bar^^http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral")
		));
		
		assertEquals(query.getType(), QueryType.SELECT_DISTINCT);
		assertEquals(query.getResultVars(), resultVars);
//		assertEquals(query.getAtomGroups().get(0).getAtoms(), atoms);
	}
	
	
	@Test
	public void testParseAsk()
		throws QueryParserException
	{
		QueryTokenizer tokenizer = new QueryTokenizerImpl();
		List<QueryToken> tokens = tokenizer.tokenize(
			"PREFIX wine: <http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#>\n" +
			"ASK { PropertyValue(?i, wine:hasColor, ?v) }"
		);
		
		QueryParser parser = new QueryParserImpl();
		Query query = parser.parse(tokens);
		
		Set<QueryArgument> resultVars = new HashSet<QueryArgument>();
		
		List<QueryAtom> atoms = new LinkedList<QueryAtom>();
		atoms.add(new QueryAtom(
			QueryAtomType.PROPERTY_VALUE, 
			new QueryArgument(QueryArgumentType.VAR, "i"),
			new QueryArgument(QueryArgumentType.URI, "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasColor"),
			new QueryArgument(QueryArgumentType.VAR, "v")
		));
		
		assertEquals(query.getType(), QueryType.ASK);
		assertEquals(query.getResultVars(), resultVars);
		assertEquals(query.getAtomGroups().get(0).getAtoms(), atoms);
	}
}
