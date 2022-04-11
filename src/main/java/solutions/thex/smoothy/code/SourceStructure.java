package solutions.thex.smoothy.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;

/**
 * Provide dedicated methods for a structure that holds sources.
 */
@Getter
public class SourceStructure {

	private final Path rootDirectory;

	private final String sourceFileExtension;

	private final Path sourcesDirectory;

	private final Path resourcesDirectory;

	public SourceStructure(Path rootDirectory) {
		this.rootDirectory = rootDirectory;
		this.sourceFileExtension = "java";
		this.sourcesDirectory = rootDirectory.resolve("java");
		this.resourcesDirectory = rootDirectory.resolve("resources");
	}

	/**
	 * Resolve a source file.
	 *
	 * @param packageName the name of the package
	 * @param fileName    the name of the file (without its extension)
	 * @return the {@link Path file} to use to store a {@code CompilationUnit} with the
	 * specified package and name
	 * @see #getSourcesDirectory()
	 */
	public Path resolveSourceFile(String packageName, String fileName) {
		String file = fileName + "." + this.sourceFileExtension;
		return resolvePackage(this.sourcesDirectory, packageName).resolve(file);
	}

	/**
	 * Create a source file, creating its package structure if necessary.
	 *
	 * @param packageName the name of the package
	 * @param fileName    the name of the file (without its extension)
	 * @return the {@link Path file} to use to store a {@code CompilationUnit} with the
	 * specified package and name
	 * @throws IOException if an error occurred while trying to create the directory
	 *                     structure or the file itself
	 * @see #getSourcesDirectory()
	 */
	public Path createSourceFile(String packageName, String fileName) throws IOException {
		Path sourceFile = resolveSourceFile(packageName, fileName);
		createFile(sourceFile);
		return sourceFile;
	}

	/**
	 * Resolve a resource file defined in the specified package.
	 *
	 * @param packageName the name of the package
	 * @param file        the name of the file (including its extension)
	 * @return the {@link Path file} to use to store a resource with the specified package
	 * @see #getResourcesDirectory()
	 */
	public Path resolveResourceFile(String packageName, String file) {
		return resolvePackage(this.resourcesDirectory, packageName).resolve(file);
	}

	/**
	 * Create a resource file, creating its package structure if necessary.
	 *
	 * @param packageName the name of the package
	 * @param file        the name of the file (including its extension)
	 * @return the {@link Path file} to use to store a resource with the specified package
	 * @throws IOException if an error occurred while trying to create the directory
	 *                     structure or the file itself
	 * @see #getResourcesDirectory()
	 */
	public Path createResourceFile(String packageName, String file) throws IOException {
		Path resourceFile = resolveResourceFile(packageName, file);
		createFile(resourceFile);
		return resourceFile;
	}

	private void createFile(Path file) throws IOException {
		Files.createDirectories(file.getParent());
		Files.deleteIfExists(file);
		Files.createFile(file);
	}

	private static Path resolvePackage(Path directory, String packageName) {
		return directory.resolve(packageName.replace('.', '/'));
	}

}
