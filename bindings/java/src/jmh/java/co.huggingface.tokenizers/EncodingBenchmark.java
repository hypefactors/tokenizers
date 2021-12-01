package co.huggingface.tokenizers;

import org.openjdk.jmh.annotations.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Threads(value = 1)
@Fork(value = 1)
public class EncodingBenchmark {

    @State(Scope.Benchmark)
    public static class ExecutionPlan {
        private TokenizerFromPretrained tokenizer;
        private String bigInput;
        private String smallInput = "The Project Gutenberg EBook of The Adventures of Sherlock Holmes\n" +
                "by Sir Arthur Conan Doyle\n" +
                "(#15 in our series by Sir Arthur Conan Doyle)\n" +
                "\n" +
                "Copyright laws are changing all over the world. Be sure to check the\n" +
                "copyright laws for your country before downloading or redistributing\n" +
                "this or any other Project Gutenberg eBook.";

        @Setup(Level.Invocation)
        public void setUp() throws IOException {
            bigInput = this.getResourceAsString("big.txt");
            tokenizer = new TokenizerFromPretrained("bert-base-cased");
        }

        String getResourceAsString(String filename) throws IOException {
            var inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = inputStream.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
            return result.toString();
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 10, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void singleInput(ExecutionPlan plan) {
        plan.tokenizer.encode(plan.smallInput, false);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void largeInput(ExecutionPlan plan)  {
        plan.tokenizer.encode(plan.bigInput, false);
    }
}