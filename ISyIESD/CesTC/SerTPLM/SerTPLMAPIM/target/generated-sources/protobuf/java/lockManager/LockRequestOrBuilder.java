// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ILockManager.proto

package lockManager;

public interface LockRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:forum.LockRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 tid = 1;</code>
   * @return The tid.
   */
  int getTid();

  /**
   * <code>repeated .forum.ResourceElement elements = 2;</code>
   */
  java.util.List<lockManager.ResourceElement> 
      getElementsList();
  /**
   * <code>repeated .forum.ResourceElement elements = 2;</code>
   */
  lockManager.ResourceElement getElements(int index);
  /**
   * <code>repeated .forum.ResourceElement elements = 2;</code>
   */
  int getElementsCount();
  /**
   * <code>repeated .forum.ResourceElement elements = 2;</code>
   */
  java.util.List<? extends lockManager.ResourceElementOrBuilder> 
      getElementsOrBuilderList();
  /**
   * <code>repeated .forum.ResourceElement elements = 2;</code>
   */
  lockManager.ResourceElementOrBuilder getElementsOrBuilder(
      int index);
}