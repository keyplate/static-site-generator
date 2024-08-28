# Static Site Generator

This project is a simple static site generator that compiles static .html pages from .md (Markdown) files. 
Also as a side quest for me it was build without statndard build tools like maven or gradle:)

## Build

To build the project, run:
```bash
$ chmod +x "build.sh"
$ ./build.sh
```

## Usage

1. Create separate directories for input and output.
2. Place your .md files hierarchy in `/{input_dir}/content`.
3. For inner links, arrange files according to your link structure. 
   Example: For a link to a labrador article within a dogs article, place the labrador article in the `/labrador` directory.
4. Prepare an HTML template for your pages. Include `{{ Content }}` where the content should be inserted.
5. Run the generator:
   ```
   $ java -cp ./build/ com.lapchenko.generator.Main "path_to_template" "path_to_input_dir" "path_to_output_dir"
   ``` 

## Test 

To run tests, run:

```bash
$ chmod +x "test.sh"
$ ./test.sh
```

## Example

**cats.md**

![image](https://github.com/user-attachments/assets/51dd0cc5-97b0-4e81-939b-e24169e15a7f)

**cats.html**

![catarticle](https://github.com/user-attachments/assets/4f87667d-43ca-4a64-b4cd-6b5c058387e2)

