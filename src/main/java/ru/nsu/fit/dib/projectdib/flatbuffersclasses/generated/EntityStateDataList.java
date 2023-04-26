// automatically generated by the FlatBuffers compiler, do not modify

package ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated;

import java.nio.*;
import java.lang.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class EntityStateDataList extends Table {

  public static void ValidateVersion() {
    Constants.FLATBUFFERS_2_0_8();
  }

  public static EntityStateDataList getRootAsEntityStateDataList(ByteBuffer _bb) {
    return getRootAsEntityStateDataList(_bb, new EntityStateDataList());
  }

  public static EntityStateDataList getRootAsEntityStateDataList(ByteBuffer _bb,
      EntityStateDataList obj) {
    _bb.order(ByteOrder.LITTLE_ENDIAN);
    return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
  }

  public void __init(int _i, ByteBuffer _bb) {
    __reset(_i, _bb);
  }

  public EntityStateDataList __assign(int _i, ByteBuffer _bb) {
    __init(_i, _bb);
    return this;
  }

  public ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData list(int j) {
    return list(new ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData(), j);
  }

  public ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData list(
      ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData obj, int j) {
    int o = __offset(4);
    return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null;
  }

  public int listLength() {
    int o = __offset(4);
    return o != 0 ? __vector_len(o) : 0;
  }

  public ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData.Vector listVector() {
    return listVector(
        new ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData.Vector());
  }

  public ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData.Vector listVector(
      ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData.Vector obj) {
    int o = __offset(4);
    return o != 0 ? obj.__assign(__vector(o), 4, bb) : null;
  }

  public static int createEntityStateDataList(FlatBufferBuilder builder,
      int listOffset) {
    builder.startTable(1);
    EntityStateDataList.addList(builder, listOffset);
    return EntityStateDataList.endEntityStateDataList(builder);
  }

  public static void startEntityStateDataList(FlatBufferBuilder builder) {
    builder.startTable(1);
  }

  public static void addList(FlatBufferBuilder builder, int listOffset) {
    builder.addOffset(0, listOffset, 0);
  }

  public static int createListVector(FlatBufferBuilder builder, int[] data) {
    builder.startVector(4, data.length, 4);
    for (int i = data.length - 1; i >= 0; i--) {
      builder.addOffset(data[i]);
    }
    return builder.endVector();
  }

  public static void startListVector(FlatBufferBuilder builder, int numElems) {
    builder.startVector(4, numElems, 4);
  }

  public static int endEntityStateDataList(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static void finishEntityStateDataListBuffer(FlatBufferBuilder builder, int offset) {
    builder.finish(offset);
  }

  public static void finishSizePrefixedEntityStateDataListBuffer(FlatBufferBuilder builder,
      int offset) {
    builder.finishSizePrefixed(offset);
  }

  public static final class Vector extends BaseVector {

    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) {
      __reset(_vector, _element_size, _bb);
      return this;
    }

    public EntityStateDataList get(int j) {
      return get(new EntityStateDataList(), j);
    }

    public EntityStateDataList get(EntityStateDataList obj, int j) {
      return obj.__assign(__indirect(__element(j), bb), bb);
    }
  }
}

