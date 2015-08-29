// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/resources/proto/command.proto

package gr.uoa.di.containermigrator.communication.protocol;

public final class Command {
  private Command() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface ReadyToRestoreOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required string image = 1;
    /**
     * <code>required string image = 1;</code>
     */
    boolean hasImage();
    /**
     * <code>required string image = 1;</code>
     */
    java.lang.String getImage();
    /**
     * <code>required string image = 1;</code>
     */
    com.google.protobuf.ByteString
        getImageBytes();

    // required bool tcpEstablished = 2;
    /**
     * <code>required bool tcpEstablished = 2;</code>
     */
    boolean hasTcpEstablished();
    /**
     * <code>required bool tcpEstablished = 2;</code>
     */
    boolean getTcpEstablished();
  }
  /**
   * Protobuf type {@code ReadyToRestore}
   */
  public static final class ReadyToRestore extends
      com.google.protobuf.GeneratedMessage
      implements ReadyToRestoreOrBuilder {
    // Use ReadyToRestore.newBuilder() to construct.
    private ReadyToRestore(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private ReadyToRestore(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final ReadyToRestore defaultInstance;
    public static ReadyToRestore getDefaultInstance() {
      return defaultInstance;
    }

    public ReadyToRestore getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private ReadyToRestore(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              image_ = input.readBytes();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              tcpEstablished_ = input.readBool();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return gr.uoa.di.containermigrator.communication.protocol.Command.internal_static_ReadyToRestore_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return gr.uoa.di.containermigrator.communication.protocol.Command.internal_static_ReadyToRestore_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore.class, gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore.Builder.class);
    }

    public static com.google.protobuf.Parser<ReadyToRestore> PARSER =
        new com.google.protobuf.AbstractParser<ReadyToRestore>() {
      public ReadyToRestore parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ReadyToRestore(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<ReadyToRestore> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string image = 1;
    public static final int IMAGE_FIELD_NUMBER = 1;
    private java.lang.Object image_;
    /**
     * <code>required string image = 1;</code>
     */
    public boolean hasImage() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string image = 1;</code>
     */
    public java.lang.String getImage() {
      java.lang.Object ref = image_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          image_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string image = 1;</code>
     */
    public com.google.protobuf.ByteString
        getImageBytes() {
      java.lang.Object ref = image_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        image_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required bool tcpEstablished = 2;
    public static final int TCPESTABLISHED_FIELD_NUMBER = 2;
    private boolean tcpEstablished_;
    /**
     * <code>required bool tcpEstablished = 2;</code>
     */
    public boolean hasTcpEstablished() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required bool tcpEstablished = 2;</code>
     */
    public boolean getTcpEstablished() {
      return tcpEstablished_;
    }

    private void initFields() {
      image_ = "";
      tcpEstablished_ = false;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasImage()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasTcpEstablished()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getImageBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBool(2, tcpEstablished_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getImageBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, tcpEstablished_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code ReadyToRestore}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestoreOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return gr.uoa.di.containermigrator.communication.protocol.Command.internal_static_ReadyToRestore_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return gr.uoa.di.containermigrator.communication.protocol.Command.internal_static_ReadyToRestore_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore.class, gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore.Builder.class);
      }

      // Construct using gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        image_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        tcpEstablished_ = false;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return gr.uoa.di.containermigrator.communication.protocol.Command.internal_static_ReadyToRestore_descriptor;
      }

      public gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore getDefaultInstanceForType() {
        return gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore.getDefaultInstance();
      }

      public gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore build() {
        gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore buildPartial() {
        gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore result = new gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.image_ = image_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.tcpEstablished_ = tcpEstablished_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore) {
          return mergeFrom((gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore other) {
        if (other == gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore.getDefaultInstance()) return this;
        if (other.hasImage()) {
          bitField0_ |= 0x00000001;
          image_ = other.image_;
          onChanged();
        }
        if (other.hasTcpEstablished()) {
          setTcpEstablished(other.getTcpEstablished());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasImage()) {
          
          return false;
        }
        if (!hasTcpEstablished()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (gr.uoa.di.containermigrator.communication.protocol.Command.ReadyToRestore) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required string image = 1;
      private java.lang.Object image_ = "";
      /**
       * <code>required string image = 1;</code>
       */
      public boolean hasImage() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string image = 1;</code>
       */
      public java.lang.String getImage() {
        java.lang.Object ref = image_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          image_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string image = 1;</code>
       */
      public com.google.protobuf.ByteString
          getImageBytes() {
        java.lang.Object ref = image_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          image_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string image = 1;</code>
       */
      public Builder setImage(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        image_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string image = 1;</code>
       */
      public Builder clearImage() {
        bitField0_ = (bitField0_ & ~0x00000001);
        image_ = getDefaultInstance().getImage();
        onChanged();
        return this;
      }
      /**
       * <code>required string image = 1;</code>
       */
      public Builder setImageBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        image_ = value;
        onChanged();
        return this;
      }

      // required bool tcpEstablished = 2;
      private boolean tcpEstablished_ ;
      /**
       * <code>required bool tcpEstablished = 2;</code>
       */
      public boolean hasTcpEstablished() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required bool tcpEstablished = 2;</code>
       */
      public boolean getTcpEstablished() {
        return tcpEstablished_;
      }
      /**
       * <code>required bool tcpEstablished = 2;</code>
       */
      public Builder setTcpEstablished(boolean value) {
        bitField0_ |= 0x00000002;
        tcpEstablished_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bool tcpEstablished = 2;</code>
       */
      public Builder clearTcpEstablished() {
        bitField0_ = (bitField0_ & ~0x00000002);
        tcpEstablished_ = false;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:ReadyToRestore)
    }

    static {
      defaultInstance = new ReadyToRestore(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:ReadyToRestore)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_ReadyToRestore_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_ReadyToRestore_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&src/main/resources/proto/command.proto" +
      "\"7\n\016ReadyToRestore\022\r\n\005image\030\001 \002(\t\022\026\n\016tcp" +
      "Established\030\002 \002(\010B4\n2gr.uoa.di.container" +
      "migrator.communication.protocol"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_ReadyToRestore_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_ReadyToRestore_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_ReadyToRestore_descriptor,
              new java.lang.String[] { "Image", "TcpEstablished", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
