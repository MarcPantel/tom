/*
 * Created on 14-sep-2004
 *
 * Copyright (c) 2004-2007, Michael Moossen
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met: 
 *  - Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.  
 *  - Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  - Neither the name of the INRIA nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.loria.protheo.psgenerator.parser;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXParseException;

import fr.loria.protheo.psgenerator.representation.FactType;
import fr.loria.protheo.psgenerator.representation.OrderedFactType;
import fr.loria.protheo.psgenerator.representation.Program;
import fr.loria.protheo.psgenerator.representation.UnorderedFactType;

/**
 *  The DomParser class
 *
 *      <p align="left"><font color="#003063"><b>Change Log</b></font>
 *      <table border="0" cellpadding="1">
 *        <tr bordercolor="#FF0000" bgcolor="#CCCCCC" align="center"> 
 *          <th width="107"><strong>Date</strong></th>
 *          <th width="67"><strong>Version</strong></th>
 *          <th width="491"><strong>Description</strong></th>
 *        </tr>
 *        <tr align="center"> 
 *          <td>14 sept 2004</td>
 *          <td>0</td>
 *          <td align="left"> 
 *            File Creation
 *          </td>
 *        </tr>
 *        <tr bgcolor="#EAEAEA"  align="center"> 
 *          <td>14 sept 2004</td>
 *          <td>0.1</td>
 *          <td align="left"> 
 *            Initial Working Version
 *          </td>
 *        </tr>
 *      </table>
 *
 * @author <a href="mailto:moossen@loria.fr">Michael Moossen</a>
 */
public class DomParser extends AbstractParser {
	private Document document;

	public DomParser(String fileName) {
		super(fileName);
	}

	public void readFile(String fileName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//factory.setValidating(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			//builder.setErrorHandler(this);
			document = builder.parse(new File(fileName));
		} catch (SAXParseException spe) {
			// Error generated by the parser
			System.out.println("\n** Parsing error" + ", line "
					+ spe.getLineNumber() + ", uri " + spe.getSystemId());
			System.out.println("   " + spe.getMessage());

			// Use the contained exception, if any
			Exception x = spe;
			if (spe.getException() != null)
				x = spe.getException();
			x.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void parse() {
		parse(document);		
	}

	private void parse(Node node) {
		String eName = node.getNodeName();
		boolean alive = true;
		if (eName.equals(PSP_SPEC)) {
			String name = node.getAttributes().getNamedItem(NAME).getNodeValue().trim();
			String version = node.getAttributes().getNamedItem(VERSION).getNodeValue().trim();
			String comments = null;
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node childNode = node.getChildNodes().item(i);
				if (childNode.getNodeName().equals(COMMENTS)) {
					comments = childNode.getTextContent().trim();
				}
			}
			program = new Program(name, version, comments);
		} else if (eName.equals(FACT_DEFINITIONS)) {
			//program.setFactTypes(new Vector());
		} else if (eName.equals(INTIAL_WORKING_MEMORY)) {
			//program.setInitialFacts(new Vector());
		} else if (eName.equals(INITIAL_PRODUCTION_MEMORY)) {
			//program.setRules(new Vector());
		} else if (eName.equals(ORDERED_FACT_DEFINITION)) {
			String name = null;
			String comment = null;
			int arity = -1;
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node childNode = node.getChildNodes().item(i);
				if (childNode.getNodeName().equals(FACT_NAME)) {
					name = childNode.getTextContent().trim();
				} else if (childNode.getNodeName().equals(FACT_ARITY)) {
					arity = Integer.parseInt(childNode.getTextContent().trim());
				} else if (childNode.getNodeName().equals(COMMENTS)) {
					comment = childNode.getTextContent().trim();
				} 
			}
			program.addFactType(new OrderedFactType(name,comment,arity));
			alive = false;
		} else if (eName.equals(UNORDERED_FACT_DEFINITION)) {
			String name = null;
			String comment = null;
			List slotNames = new Vector();
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node childNode = node.getChildNodes().item(i);
				if (childNode.getNodeName().equals(FACT_NAME)) {
					name = childNode.getTextContent().trim();
				} else if (childNode.getNodeName().equals(SLOT_NAME)) {
					slotNames.add(childNode.getTextContent().trim());
				} else if (childNode.getNodeName().equals(COMMENTS)) {
					comment = childNode.getTextContent().trim();
				} 
			}
			program.addFactType(new UnorderedFactType(name,comment,slotNames));
			alive = false;
		} else if (eName.equals(ORDERED_FACT_INSTANCE)) {
			String name = null;
			String comment = null;
			FactType type = null;
			List slotValues = new Vector();
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node childNode = node.getChildNodes().item(i);
				if (childNode.getNodeName().equals(FACT_NAME)) {
					name = childNode.getTextContent().trim();
					type = program.getFactType(name);
					if (type.getType()!=FactType.ORDERED_TYPE) {
						throw new IllegalStateException("FactType "+name+" is not ordered");
					}
				} else if (childNode.getNodeName().equals(SLOT_VALUE)) {
					slotValues.add(childNode.getTextContent().trim());
				} else if (childNode.getNodeName().equals(COMMENTS)) {
					comment = childNode.getTextContent().trim();
				} 
			}
			if (node.getParentNode().getNodeName().equals(INTIAL_WORKING_MEMORY)) {
				program.addInitialFact(type.newInstance(comment,slotValues));
			}
			alive = false;
		} else if (eName.equals(UNORDERED_FACT_INSTANCE)) {
			String name = null;
			String comment = null;
			FactType type = null;
			Map slots = Collections.checkedMap(new HashMap(), String.class, String.class);
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node childNode = node.getChildNodes().item(i);
				if (childNode.getNodeName().equals(FACT_NAME)) {
					name = childNode.getTextContent().trim();
					type = program.getFactType(name);
					if (type.getType()!=FactType.UNORDERED_TYPE) {
						throw new IllegalStateException("FactType "+name+" is not unordered");
					}
				} else if (childNode.getNodeName().equals(SLOT)) {
					String slotName = null;
					String slotValue = null;
					for (int j = 0; j < childNode.getChildNodes().getLength(); j++) {
						Node grandChildNode = childNode.getChildNodes().item(j);
						if (grandChildNode.getNodeName().equals(SLOT_NAME)) {
							slotName = grandChildNode.getTextContent().trim();
						} else if (grandChildNode.getNodeName().equals(SLOT_VALUE)) {
							slotValue = grandChildNode.getTextContent().trim();
						}
					}
					slots.put(slotName, slotValue);
				} else if (childNode.getNodeName().equals(COMMENTS)) {
					comment = childNode.getTextContent().trim();
				} 
			}
			if (node.getParentNode().getNodeName().equals(INTIAL_WORKING_MEMORY)) {
				program.addInitialFact(type.newInstance(comment,slots));
			}
			alive = false;
		} else if (eName.equals(ORDERED_PATTERN)) {
			//
		} else if (eName.equals(UNORDERED_PATTERN)) {
			//
		} else if (eName.equals(ORDERED_NEGATIVE_PATTERN)) {
			//
		} else if (eName.equals(UNORDERED_NEGATIVE_PATTERN)) {
			//
		} else if (eName.equals(PRODUCTION_RULE)) {
			//
		} else if (eName.equals(RULE_NAME)) {
			//
		} else if (eName.equals(LEFT_HAND_SIDE)) {
			//
		} else if (eName.equals(RIGHT_HAND_SIDE)) {
			//
		} else if (eName.equals(PATTERNS)) {
			//
		} else if (eName.equals(CONDITIONS)) {
			//
		} else if (eName.equals(CONDITION)) {
			//
		} else if (eName.equals(RELATION)) {
			//
		} else if (eName.equals(EXPRESSION)) {
			//
		} else if (eName.equals(FACTS_TO_REMOVE)) {
			//
		} else if (eName.equals(FACTS_TO_ADD)) {
			//
		}
		if (alive) {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				parse(node.getChildNodes().item(i));
			}
		}
	}
}