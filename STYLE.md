_This document explains expected coding standards and conventions._

# Backend: Java, Spring Boot
## Java: Naming Expectations
### Classes
1. **Capitalize the first letter of each word of a class:** Example_Class or ExampleClass
2. **Use descriptive class names:** ASDF vs. Player

### Variables
1. **For static constants, use all upper case:** MAX_SCORE
2. **For global variables, include "global" in the variable name:** global_variable or globalVariable
3. **For all other variables, use:** underscore_case or camelCase
4. **Use descriptive names for variables:** asfd vs. array_index
5. **Be clear what a boolean value returns:** check_input vs. input_is_valid 

## Java: Commenting Expectations
### File Header Comments
Explain what the file does or what the goal of the file is.
```java
/**
 * This is where you describe the file function or goal. 
 */
```

### Function Header Comments
Explain the goal of the function and include descriptions of the input variables and the return value.
```java
/**
 * This is where you describe the function.
 *
 * @param input_a this is where you describe an input variable
 * @return 
 */
```

### In-Function Comments
Comment regularly but within reason. Focus on what your doing and why rather than explaining what can easily be inferred from a line of code. 
```java
/* Unnecessary Comment */
int a = b + c; // a equals b plus c
```

## Java: Coding Expectations
1. **Verify that inputs are valid**
2. **Indent to show structure and prevent errors, especially when using loops** 
3. **Use parenthesis to prevent ambiguity**
4. **Break up complex expressions to multiple lines**
5. **Be clear and intentional in your code**  

```java
//////////////////
// EXAMPLE CODE //
//////////////////
  /**
   * Checks if the mouse is over a specific Badger whose reference is provided as input parameter
   * 
   * @param Badger reference to a specific Badger object
   * @return true if the mouse is over the specific Badger object passed as input (i.e. over the
   *         image of the Badger), false otherwise
   */
  public static boolean isMouseOver(Badger Badger) {
    // Avoids null pointer error if there is no Badger present
    if (Badger != null) {
      PImage badger = Badger.image();
      // Checks if mouse is within x-coordinate bounds of a badger
      if (Utility.mouseX() <= Badger.getX() + ((badger.width) / 2)
          && Utility.mouseX() >= Badger.getX() - ((badger.width) / 2)) {
        // Checks if mouse is within y-coordinate bounds of a badger
        if (Utility.mouseY() <= Badger.getY() + ((badger.height) / 2)
            && Utility.mouseY() >= Badger.getY() - ((badger.height) / 2)) {
          return true;
        }
      }
    }
    // Returns false if mouse is not over a badger
    return false;
  }
```

# Frontend: React, Typescript
## React: Naming Expectations
### Components
1. **Add the word "Component" at the end of a component name:** ButtonComponent vs. Button
2. **Capitalize the first letter of each word of a component, and use Pascal (upper camel) case naming conventions:** ExampleComponent vs. Example_Component, example_component, or exampleComponent
3. **Use descriptive component names:** WordleLetterComponent vs. ASDFComponent
4. **The corresponding file name should have the same name as the component:** ButtonComponent.tsx vs. AnotherComponent.tsx or ComponentLibraryFile.tsx

### Props
1. **The name of a prop should be the name of its corresponding component followed by the word "Props":** ButtonComponentProps vs. BCProps or ButtonComponentAttributes
2. **Capitalize the first letter of each word of a prop, and use Pascal (upper camel) case naming conventions:** ExampleProp vs. Example_Prop, example_prop, or exampleProp

### Variables
1. **For static constants, use all upper case:** MAX_SCORE
2. **For global variables, include "global" in the variable name:** global_variable or globalVariable
3. **For all other variables, use:** underscore_case or camelCase
4. **Use descriptive names for variables:** asfd vs. array_index
5. **Be clear what a boolean value returns:** check_input vs. input_is_valid

## React: Commenting Expectations
### File Header Comments
Explain what the file does or what the goal of the file is. This is primarily for long and complex files; if the file only contains the function for returning a component, a function header comment may suffice.
```typescript
/**
 * This is where you describe the file function or goal. 
 */
```

### Function Header Comments
Explain the goal of the function and include descriptions of the input variables and the return value. This is especially important for component functions.
```typescript
/**
 * This is where you describe the function.
 *
 * @param input_a this is where you describe an input variable
 * @returns the value that is returned
 */
```

### In-Function Comments
Comment regularly but within reason. Focus on what your doing and why rather than explaining what can easily be inferred from a line of code.
```typescript
/* Unnecessary Comment */
let a = b + c; // a equals b plus c
```

## React: Component Conventions
Inside the `components` directory, we will have three subdirectories: `atoms`, `molecules`, and `pages`:
* **Atom component:** a small, basic component that cannot be broken down further, often used as building blocks for more complex components. (E.g. ButtonComponent, WordleLetterComponent)
* **Molecule component:** a composite component made up of multiple atom components for a certain utility. (E.g. WordleGuessComponent)
* **Page component:** a component for a whole page in the application, contains multiple molecule components. (E.g. LoginPageComponent, WordleGamePageComponent)

We will distinguish between these component types for the purpose of abstraction and to reduce information leakage.

We are using function components: every component we use must be constructed and returned in a function. The function must be exported and default for a file, and both the function and file must share the same name as the component. (This means that each file will contain the function for *at most* one component.)

The props for each component should be defined in the same file as the corresponding component, e.g. ButtonComponentProps is defined in ButtonComponent.tsx. Exceptions may exist, e.g. a default props type used for components that don't need any specific prop attributes.

We will have one CSS file for the entire application to maintain abstraction and prevent information leakage. For ease of using React components with CSS styling, each component type must have either a specific className value or a finite set of possible className values, picked using a prop value. No two different component types may share a same className value.

## React: Coding Expectations
1. **Verify that inputs are valid**
2. **Indent to show structure and prevent errors, especially when using large HTML blocks**
3. **Use parenthesis to prevent ambiguity**
4. **Break up complex expressions to multiple lines**
5. **Be clear and intentional in your code**
```typescript
//////////////////
// EXAMPLE CODE //
//////////////////
  /**
   * Creates a component for a row of `props.count` buttons, zero-indexed. They
   * can be used to pass any function taking that number as its parameter.
   *
   * @param props the button row component props, containing the number of
   * buttons in the `count` field and optionally the function to pass for each
   * button in the `functionality` field.
   * @returns a button row component
   */
  export default function ButtonRowComponent(props: ButtonRowComponentProps) {
    // Ensure nonnegative number of buttons
    if (props.count < 0)
      throw new RangeError(f"ButtonRowComponent count is {props.count} but must be at least 0");

    // Create list of buttons, adding functionality if provided
    const buttonRow = [];
    for (let i: number = 0; i < props.count; i++)
      buttonRow[i] = props.functionality !== undefined ? <ButtonComponent label={i} onClick={() => props.functionality(i)} /> : <ButtonComponent label={i} />;
    return (
      <div className="buttonrow">
        {buttonRow}
      </div>
    );
  }

```