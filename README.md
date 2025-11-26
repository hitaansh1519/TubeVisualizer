# 📐 Rectangular Tube Joint Visualizer

An interactive application designed to visualize and analyze miter joints between rectangular or square tubes at various custom angles.

## 🎯 Features

This project provides two distinct implementations:

- **Web/3D Visualization (Three.js)**: A single-file web application for interactive 3D inspection, bundled via Electron for desktop use
- **Desktop/2D Visualization (Java Swing)**: A single-file Java application demonstrating geometric calculations and a 2D cross-section view

## 🚀 Getting Started

### Prerequisites

| Implementation | Requirements |
|---------------|--------------|
| **Web/Electron (3D)** | Node.js (for building the executable) and a Modern Web Browser (for development/testing) |
| **Desktop (Java 2D)** | Java Development Kit (JDK 17+) to compile and run the source file |

### Source Files

| Implementation | Path | Description |
|---------------|------|-------------|
| **Java Desktop (2D)** | `src/desktop/TubeJointVisualizer.java` | Requires JDK to compile and run |
| **Web Visualization (3D)** | `src/web/index.html` | Packaged using Electron for standalone desktop use |

## 🖼️ Application Gallery

### 3D Web/Electron Visualization (Initial View)

The 3D view shows the control panel (left) and the interactive three-dimensional tubes. This is the output of the packaged Electron app.

![3D Electron View](https://github.com/user-attachments/assets/9815a81e-3e7f-4441-a898-764b2bb84534)

### 2D Java Desktop Visualization (90° Joint)

This view highlights the Java Swing interface, showing the input parameters, the calculated Miter Cut Angle (α), and the 2D cross-section for a 90-degree internal joint angle (θ).

![Java 90° Joint](https://github.com/user-attachments/assets/c877b4f8-8001-469c-81fa-a1f27101bcd1)

### 2D Java Desktop Visualization (135° Joint)

This demonstrates the output when the internal joint angle (θ) is set to 135 degrees, resulting in a 67.5-degree Miter Cut Angle (α).

![Java 135° Joint](https://github.com/user-attachments/assets/27fa9bb3-7708-48c4-8ee3-9bc6070684e6)

## 🖥️ Java Desktop Application

This application demonstrates the geometric calculation using a 2D cross-section view.

### Build and Run Instructions

1. Navigate to the desktop source directory:
   ```bash
   cd src/desktop
   ```

2. Compile the Java file:
   ```bash
   javac TubeJointVisualizer.java
   ```

3. Run the application:
   ```bash
   java TubeJointVisualizer
   ```

## 📦 Electron Packaging Steps

This process bundles the 3D Web Visualization (`src/web/index.html`) into a single, standalone executable for deployment.

### 1. Install Dependencies

From the root directory of the project (`/tube-joint-visualizer`), install the required development tools as defined in `package.json`:

```bash
npm install
```

### 2. Build the Application

Use the custom script defined in `package.json` to start the build process. This will create platform-specific executables (Windows, macOS, Linux).

```bash
npm run build:electron
```

### 3. Locate the Executable File

The `electron-builder` tool places all output files into the newly created `dist` directory at the project root.

| Operating System | Output File Location Example |
|-----------------|------------------------------|
| **Windows** | `dist/Tube Joint Visualizer Setup 1.0.0.exe` |
| **macOS** | `dist/Tube Joint Visualizer-1.0.0.dmg` |
| **Linux** | `dist/Tube Joint Visualizer-1.0.0.AppImage` |

## 📝 Commit Message Guidelines

This project uses [Conventional Commits](https://www.conventionalcommits.org/). Please follow this format for clear version history:

| Type | Description | Example |
|------|-------------|---------|
| `feat` | A new feature or capability | `feat(electron): add mac build target` |
| `fix` | A bug fix | `fix(java): correct validation for wall thickness` |
| `refactor` | Code changes that neither fix a bug nor add a feature | `refactor(web): improve updateJoint calculation logic` |
| `docs` | Documentation only changes | `docs: finalize README with packaging instructions` |

## 📄 License

This project is provided as-is for educational and practical purposes.

## 🤝 Contributing

Contributions are welcome! Please follow the commit message guidelines and ensure all tests pass before submitting a pull request.
