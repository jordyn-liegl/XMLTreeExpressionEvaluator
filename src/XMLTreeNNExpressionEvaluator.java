import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Jordyn Liegl
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        // TODO - fill in body

        //write a series of if statements to see the operation of the label
        //of the child
        if (exp.label().equals("plus")) {
            NaturalNumber child0 = evaluate(exp.child(0));
            child0.add(evaluate(exp.child(1)));
            return child0;
        }

        if (exp.label().equals("divide")) {
            NaturalNumber child0 = evaluate(exp.child(0));
            NaturalNumber child1 = evaluate(exp.child(1));
            if (child1.canConvertToInt() && child1.toInt() == 0) {
                Reporter.fatalErrorToConsole("Error: Division By Zero");
            }
            child0.divide(child1);
            return child0;
        }

        if (exp.label().equals("times")) {
            NaturalNumber child0 = evaluate(exp.child(0));
            child0.multiply(evaluate(exp.child(1)));
            return child0;
        }

        if (exp.label().equals("minus")) {
            NaturalNumber child0 = evaluate(exp.child(0));
            NaturalNumber child1 = evaluate(exp.child(1));
            if (child1.compareTo(child0) > 0) {
                Reporter.fatalErrorToConsole(
                        "Error: Negative Natural Number Created");
            }
            child0.subtract(child1);
            return child0;
        }

        if (exp.label().equals("number")) {
            return new NaturalNumber1L(exp.attributeValue("value"));
        } else {
            return new NaturalNumber1L("0");
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}