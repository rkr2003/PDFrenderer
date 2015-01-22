package com.sun.pdfview.function.postscript;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.junit.Assert;
import org.junit.Test;
import com.sun.pdfview.function.postscript.operation.OperationSet;
import com.sun.pdfview.function.postscript.operation.PostScriptOperation;

public class PostScriptOPTest {

    public static Stack<Object> parse(final String text) {
        final Stack<Object> stack = new Stack<Object>();
        final PostScriptParser p = new PostScriptParser();
        final List<String> tokens = p.parse(text);
        for (final Iterator<String> iterator = tokens.iterator(); iterator.hasNext();) {
            final String token = iterator.next();
            final PostScriptOperation op = OperationSet.getInstance().getOperation(token);
            op.eval(stack);
        }
        return stack;
    }

    @Test
    public void testRoll() {
        Stack<Object> stack = PostScriptOPTest.parse("1 2 3 4 5 5 -2 roll");
        Assert.assertEquals(2, ((Number)stack.pop()).intValue());
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertEquals(5, ((Number)stack.pop()).intValue());
        Assert.assertEquals(4, ((Number)stack.pop()).intValue());
        Assert.assertEquals(3, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());

        stack = PostScriptOPTest.parse("1 2 3 4 5 5 2 roll");
        Assert.assertEquals(3, ((Number)stack.pop()).intValue());
        Assert.assertEquals(2, ((Number)stack.pop()).intValue());
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertEquals(5, ((Number)stack.pop()).intValue());
        Assert.assertEquals(4, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());

        stack = PostScriptOPTest.parse("1 2 3 4 5 5 7 roll");
        Assert.assertEquals(3, ((Number)stack.pop()).intValue());
        Assert.assertEquals(2, ((Number)stack.pop()).intValue());
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertEquals(5, ((Number)stack.pop()).intValue());
        Assert.assertEquals(4, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());

        stack = PostScriptOPTest.parse("1 2 3 4 5 5 0 roll");
        Assert.assertEquals(5, ((Number)stack.pop()).intValue());
        Assert.assertEquals(4, ((Number)stack.pop()).intValue());
        Assert.assertEquals(3, ((Number)stack.pop()).intValue());
        Assert.assertEquals(2, ((Number)stack.pop()).intValue());
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void testIndex() {
        Stack<Object> stack = PostScriptOPTest.parse("1 0 index");
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());

        stack = PostScriptOPTest.parse("1 2 3 1 index");
        Assert.assertEquals(2, ((Number)stack.pop()).intValue());
        Assert.assertEquals(3, ((Number)stack.pop()).intValue());
    }

    @Test
    public void testExch() {
        Stack<Object> stack = PostScriptOPTest.parse("1 0 exch");
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertEquals(0, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());

        stack = PostScriptOPTest.parse("1 3.1 0 exch");
        Assert.assertEquals(3.1, ((Number)stack.pop()).doubleValue(), 1e-16);
        Assert.assertEquals(0, ((Number)stack.pop()).intValue());
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void testSub() {
        Stack<Object> stack = PostScriptOPTest.parse("1 2 sub");
        Assert.assertEquals(-1, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());

        stack = PostScriptOPTest.parse("6.3 2 sub");
        Assert.assertEquals(4.3, ((Number)stack.pop()).doubleValue(), 1e-16);
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void testDup() {
        final Stack<Object> stack = PostScriptOPTest.parse("1 2 dup");
        Assert.assertEquals(2, ((Number)stack.pop()).intValue());
        Assert.assertEquals(2, ((Number)stack.pop()).intValue());
        Assert.assertEquals(1, ((Number)stack.pop()).intValue());
        Assert.assertTrue(stack.isEmpty());
    }

}
