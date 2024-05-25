// Generated from MiniPascal.g4 by ANTLR 4.13.0

    package antlr;
    import java.util.HashMap;
    import java.util.Scanner;
    import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniPascalParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniPascalVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MiniPascalParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#programHeading}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramHeading(MiniPascalParser.ProgramHeadingContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(MiniPascalParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MiniPascalParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(MiniPascalParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#constantDefinitionPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDefinitionPart(MiniPascalParser.ConstantDefinitionPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#constantDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDefinition(MiniPascalParser.ConstantDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#constantChr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantChr(MiniPascalParser.ConstantChrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(MiniPascalParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#unsignedNumber}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsignedNumber(MiniPascalParser.UnsignedNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#unsignedInteger}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsignedInteger(MiniPascalParser.UnsignedIntegerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#sign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSign(MiniPascalParser.SignContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#bool_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_(MiniPascalParser.Bool_Context ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(MiniPascalParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#character}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter(MiniPascalParser.CharacterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#type_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_(MiniPascalParser.Type_Context ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#structuredType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructuredType(MiniPascalParser.StructuredTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#unpackedStructuredType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnpackedStructuredType(MiniPascalParser.UnpackedStructuredTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(MiniPascalParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(MiniPascalParser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#indexType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexType(MiniPascalParser.IndexTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#componentType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponentType(MiniPascalParser.ComponentTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalar}
	 * labeled alternative in {@link MiniPascalParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalar(MiniPascalParser.ScalarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subType}
	 * labeled alternative in {@link MiniPascalParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubType(MiniPascalParser.SubTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identType}
	 * labeled alternative in {@link MiniPascalParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentType(MiniPascalParser.IdentTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sType}
	 * labeled alternative in {@link MiniPascalParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSType(MiniPascalParser.STypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#scalarType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarType(MiniPascalParser.ScalarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#subrangeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubrangeType(MiniPascalParser.SubrangeTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#typeIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeIdentifier(MiniPascalParser.TypeIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#stringtype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringtype(MiniPascalParser.StringtypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#variableDeclarationPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarationPart(MiniPascalParser.VariableDeclarationPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MiniPascalParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#functionDeclarationPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclarationPart(MiniPascalParser.FunctionDeclarationPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#procedureDeclarationPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDeclarationPart(MiniPascalParser.ProcedureDeclarationPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#mainFunctionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainFunctionDeclaration(MiniPascalParser.MainFunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#mainProcedureDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainProcedureDeclaration(MiniPascalParser.MainProcedureDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(MiniPascalParser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#formalParameterSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterSection(MiniPascalParser.FormalParameterSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#parameterGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterGroup(MiniPascalParser.ParameterGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#identifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierList(MiniPascalParser.IdentifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(MiniPascalParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#procedureDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDeclaration(MiniPascalParser.ProcedureDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#resultType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResultType(MiniPascalParser.ResultTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MiniPascalParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#unlabelledStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnlabelledStatement(MiniPascalParser.UnlabelledStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#simpleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStatement(MiniPascalParser.SimpleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(MiniPascalParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(MiniPascalParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MiniPascalParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#relationaloperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationaloperator(MiniPascalParser.RelationaloperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#simpleExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExpression(MiniPascalParser.SimpleExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#additiveoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveoperator(MiniPascalParser.AdditiveoperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(MiniPascalParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#multiplicativeoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeoperator(MiniPascalParser.MultiplicativeoperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#signedFactor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignedFactor(MiniPascalParser.SignedFactorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code factVar}
	 * labeled alternative in {@link MiniPascalParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactVar(MiniPascalParser.FactVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link MiniPascalParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpr(MiniPascalParser.ParExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcDesignator}
	 * labeled alternative in {@link MiniPascalParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDesignator(MiniPascalParser.FuncDesignatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unsignedConst}
	 * labeled alternative in {@link MiniPascalParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsignedConst(MiniPascalParser.UnsignedConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notFactor}
	 * labeled alternative in {@link MiniPascalParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotFactor(MiniPascalParser.NotFactorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code factBool}
	 * labeled alternative in {@link MiniPascalParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactBool(MiniPascalParser.FactBoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#unsignedConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsignedConstant(MiniPascalParser.UnsignedConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#functionDesignator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDesignator(MiniPascalParser.FunctionDesignatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(MiniPascalParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code writer}
	 * labeled alternative in {@link MiniPascalParser#procedureStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriter(MiniPascalParser.WriterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code reader}
	 * labeled alternative in {@link MiniPascalParser#procedureStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReader(MiniPascalParser.ReaderContext ctx);
	/**
	 * Visit a parse tree produced by the {@code procStatement}
	 * labeled alternative in {@link MiniPascalParser#procedureStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcStatement(MiniPascalParser.ProcStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#write}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite(MiniPascalParser.WriteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#writeContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteContent(MiniPascalParser.WriteContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#writeVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteVar(MiniPascalParser.WriteVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#read}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(MiniPascalParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#actualParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActualParameter(MiniPascalParser.ActualParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#parameterwidth}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterwidth(MiniPascalParser.ParameterwidthContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#emptyStatement_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement_(MiniPascalParser.EmptyStatement_Context ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#structuredStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructuredStatement(MiniPascalParser.StructuredStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(MiniPascalParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(MiniPascalParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#conditionalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalStatement(MiniPascalParser.ConditionalStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MiniPascalParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#repetetiveStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepetetiveStatement(MiniPascalParser.RepetetiveStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MiniPascalParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#repeatStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatStatement(MiniPascalParser.RepeatStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MiniPascalParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#forList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForList(MiniPascalParser.ForListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#breakd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakd(MiniPascalParser.BreakdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#continued}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinued(MiniPascalParser.ContinuedContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#initialValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitialValue(MiniPascalParser.InitialValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniPascalParser#finalValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinalValue(MiniPascalParser.FinalValueContext ctx);
}