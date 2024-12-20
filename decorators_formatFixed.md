# Decorators in TypeScript

Decorators in TypeScript are a powerful feature that allows you to add behavior to classes, methods, properties, and parameters. They can be used for logging, validation, or adding metadata, among many other things.

## Types of Decorators:

- **Class Decorators**: Modify or add behavior to a class.
- **Method Decorators**: Modify methods and their behavior.
- **Property Decorators**: Modify or observe class properties.
- **Parameter Decorators**: Modify parameters.
- **Accessor Decorators**: Modify getter and setter behavior.

## Pros of Decorators:

- **Separation of Concerns**: Isolates cross-cutting logic (e.g., logging, validation) from core business logic.
- **Reusability**: A single decorator can be reused across different methods or classes.
- **Cleaner Code**: Reduces boilerplate code by abstracting common behavior into decorators.
- **Improved Readability**: Makes intentions clear, such as `@log` for logging or `@cache` for caching.
- **Extensibility**: Easily add new functionality without modifying existing code.
- **Meta-programming**: Allows dynamic behavior changes at runtime (e.g., with frameworks). (SpringBoot is a good example)

## Cons of Decorators:

- **Complexity**: Overusing decorators can make code harder to understand.
- **Debugging Difficulty**: Debugging can be trickier as decorators introduce additional layers of abstraction.
- **Hidden Logic**: Decorators can hide behavior, making it harder to track where certain actions are happening.
- **Performance Overhead**: Can introduce slight performance costs due to reflection or runtime modifications.
- **Limited Support**: Some environments may not natively support decorators (e.g., older versions of TypeScript).

---

## Example Code:

### Class Decorators

The `logClass` class decorator logs a message to the console whenever the class is created.

```typescript
function logClass(target: Function) {
    console.log(`Class ${target.name} has been created`);
}

// Apply the decorator to a class
@logClass
class MyClass {
    greet() {
        return "Endry!";
    }
}

// Create an instance of the class
const obj = new MyClass();
console.log(obj.greet());  // Output: Endry!

```

### Method Decorator

The `log` method decorator logs the method name, its arguments, and the return value whenever the decorated method is called.

```typescript
function log(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  const originalMethod = descriptor.value;
  
  // Replace the method with a new function that logs input/output
  descriptor.value = function(...args: any[]) {
    console.log(`Method ${propertyKey} called with arguments:`, args);
    const result = originalMethod.apply(this, args);
    console.log(`Method ${propertyKey} returned:`, result);
    return result;
  };
}

class Greeter {
  greeting: string;
  
  constructor(message: string) {
    this.greeting = message;
  }

  // Apply the log decorator to this method
  @log
  greet(name: string) {
    return `Hello, ${this.greeting} ${name}`;
  }
}

const greeter = new Greeter("World");
greeter.greet("Alice");  // This will trigger the decorator and log the method call

```

### Accessor Decorator

The `configurable` decorator prevents modification of getter and setter behavior after they have been set.

```typescript
class Point {
  private _x: number;
  private _y: number;
  
  constructor(x: number, y: number) {
    this._x = x;
    this._y = y;
  }

  @configurable(false)
  get x() {
    return this._x;
  }

  @configurable(false)
  get y() {
    return this._y;
  }
}

function configurable(value: boolean) {
  return function (target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    descriptor.configurable = value;
  };
}

```
### Property Decorator

The `logProperty` decorator logs when a property is defined.

```typescript
function logProperty(target: any, propertyKey: string) {
  console.log(`Property ${propertyKey} has been defined`);
}

class MyClass1 {
  @logProperty
  name: string;

  constructor(name: string) {
    this.name = name;
  }
}

const obj1 = new MyClass1("Alice");  // Logs the property definition

```

### Parameter Decorator

The `trackParameter` decorator logs when a method parameter is used.

```typescript
function trackParameter(target: any, methodName: string, parameterIndex: number) {
  console.log(`Parameter at index ${parameterIndex} in method ${methodName} is tracked`);
}

class MyClass3 {
  sayHi(@trackParameter userName: string) {
    return `Hello, ${userName}!`;
  }
}

const instance = new MyClass3();
instance.sayHi("Bob");

```