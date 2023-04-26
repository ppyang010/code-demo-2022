import java.util.Scanner;

public class Calculator2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            try {
                double result = eval(input);
                System.out.printf("%.3f\n", result);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * 将表达式字符串解析为AST，并计算结果
     *
     * @param expression 表达式字符串
     * @return 计算结果
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    public static double eval(String expression) throws IllegalArgumentException {
        expression = expression.replaceAll("\\s", ""); // 移除输入字符串前后的空格
        Tokenizer tokenizer = new Tokenizer(expression);
        ASTNode node = parseExpression(tokenizer);
        if (tokenizer.hasNext()) {
            throw new IllegalArgumentException("Unexpected token: " + tokenizer.next());
        }
        return node.eval();
    }

    /**
     * 将表达式字符串解析为AST表示，支持 +、-、*、/、() 运算
     *
     * @param tokenizer 分词器
     * @return AST表示
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    private static ASTNode parseExpression(Tokenizer tokenizer) throws IllegalArgumentException {
        ASTNode node = parseTerm(tokenizer);
        while (true) {
            if (tokenizer.hasNext("+")) {
                tokenizer.next();
                node = new AddNode(node, parseTerm(tokenizer));
            } else if (tokenizer.hasNext("-")) {
                tokenizer.next();
                node = new SubtractNode(node, parseTerm(tokenizer));
            } else {
                return node;
            }
        }
    }

    /**
     * 将表达式字符串解析为AST表示，支持 *、/ 运算
     *
     * @param tokenizer 分词器
     * @return AST表示
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    private static ASTNode parseTerm(Tokenizer tokenizer) throws IllegalArgumentException {
        ASTNode node = parseFactor(tokenizer);
        while (true) {
            if (tokenizer.hasNext("*")) {
                tokenizer.next();
                node = new MultiplyNode(node, parseFactor(tokenizer));
            } else if (tokenizer.hasNext("/")) {
                tokenizer.next();
                node = new DivideNode(node, parseFactor(tokenizer));
            } else {
                return node;
            }
        }
    }

    /**
     * 将表达式字符串解析为AST表示，支持数字和括号
     *
     * @param tokenizer 分词器
     * @return AST表示
     * @throws IllegalArgumentException 如果表达式无法计算或存在非法字符
     */
    private static ASTNode parseFactor(Tokenizer tokenizer) throws IllegalArgumentException {
        if (tokenizer.hasNext("(")) {
            tokenizer.next();
            ASTNode node = parseExpression(tokenizer);
            if (!tokenizer.hasNext(")")) {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            tokenizer.next();
            return node;
        } else if (tokenizer.hasNextNumber()) {
            double value = tokenizer.nextNumber();
            return new NumberNode(value);
        } else {
            throw new IllegalArgumentException("Unexpected token: " + tokenizer.next());
        }
    }

    /**
     * 分词器，用于将表达式字符串拆分为标记
     */
    private static class Tokenizer {
        private final String input;
        private int position;

        public Tokenizer(String input) {
            this.input = input;
            this.position = 0;
        }

        public boolean hasNext() {
            return position < input.length();
        }

        public boolean hasNext(String token) {
            return input.startsWith(token, position);
        }

        public boolean hasNextNumber() {
            int dotCount = 0; // 小数点计数器
            int startPosition = position;
            while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
                if (input.charAt(position) == '.') {
                    dotCount++;
                }
                position++;
            }
            if (dotCount > 1) { // 不能有多个小数点
                throw new IllegalArgumentException("Invalid number: " + input.substring(startPosition, position));
            }
            return position > startPosition;
        }

        public double nextNumber() {
            int startPosition = position;
            while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
                position++;
            }
            return Double.parseDouble(input.substring(startPosition, position));
        }

        public String next() {
            if (!hasNext()) {
                throw new IllegalArgumentException("Unexpected end of input");
            }
            char ch = input.charAt(position);
            position++;
            return Character.toString(ch);
        }
    }

    /**
     * AST节点，用于表示表达式的一部分
     */
    private abstract static class ASTNode {
        public abstract double eval();
    }

    /**
     * 数字节点，用于表示一个数字
     */
    private static class NumberNode extends ASTNode {
        private final double value;

        public NumberNode(double value) {
            this.value = value;
        }

        public double eval() {
            return value;
        }
    }

    /**
     * 加法节点，用于表示加法操作
     */
    private static class AddNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public AddNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        public double eval() {
            return left.eval() + right.eval();
        }
    }

    /**
     * 减法节点，用于表示减法操作
     */
    private static class SubtractNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public SubtractNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        public double eval() {
            return left.eval() - right.eval();
        }
    }

    /**
     * 乘法节点，用于表示乘法操作
     */
    private static class MultiplyNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public MultiplyNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        public double eval() {
            return left.eval() * right.eval();
        }
    }

    /**
     * 除法节点，用于表示除法操作
     */
    private static class DivideNode extends ASTNode {
        private final ASTNode left;
        private final ASTNode right;

        public DivideNode(ASTNode left, ASTNode right) {
            this.left = left;
            this.right = right;
        }

        public double eval() {
            double divisor = right.eval();
            if (divisor == 0) {
                throw new IllegalArgumentException("Division by zero");
            }
            return left.eval() / divisor;
        }
    }
}