// automatically generated by the FlatBuffers compiler, do not modify

package ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class SpawnActionData extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_8(); }
  public static SpawnActionData getRootAsSpawnActionData(ByteBuffer _bb) { return getRootAsSpawnActionData(_bb, new SpawnActionData()); }
  public static SpawnActionData getRootAsSpawnActionData(ByteBuffer _bb, SpawnActionData obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public SpawnActionData __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.NewEntityData data() { return data(new ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.NewEntityData()); }
  public ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.NewEntityData data(ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.NewEntityData obj) { int o = __offset(4); return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null; }

  public static int createSpawnActionData(FlatBufferBuilder builder,
      int dataOffset) {
    builder.startTable(1);
    SpawnActionData.addData(builder, dataOffset);
    return SpawnActionData.endSpawnActionData(builder);
  }

  public static void startSpawnActionData(FlatBufferBuilder builder) { builder.startTable(1); }
  public static void addData(FlatBufferBuilder builder, int dataOffset) { builder.addOffset(0, dataOffset, 0); }
  public static int endSpawnActionData(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishSpawnActionDataBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedSpawnActionDataBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public SpawnActionData get(int j) { return get(new SpawnActionData(), j); }
    public SpawnActionData get(SpawnActionData obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

