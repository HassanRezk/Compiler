package parser;

import java.util.ArrayList;
import java.util.List;
import grammar.GrammarNode;

public class ParseTreeNode {

	private GrammarNode Value ; 
	private List<ParseTreeNode> children = new ArrayList<>();

	public ParseTreeNode(GrammarNode value ) {
		this.Value= value ;
	}

	public GrammarNode getValue (){
		return Value ;
	}
	
	public List<ParseTreeNode> getChildren (){
		return children;
	}
	
	public void addChild(ParseTreeNode child) {
		children.add(child);
	}

}
